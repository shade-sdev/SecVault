package viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import core.AppState
import core.external.google.GoogleAuthManager
import core.job.BackupJob
import core.models.*
import core.models.criteria.CredentialSearchCriteria
import core.security.AuthenticationManager
import core.security.SecurityContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import org.slf4j.Logger
import repository.creditcard.CreditCard
import repository.creditcard.CreditCardRepository
import repository.creditcard.projection.CreditCardSummary
import repository.password.Password
import repository.password.PasswordRepository
import repository.password.projection.PasswordSummary
import java.util.*

class SecVaultScreenModel(
    private val passwordRepository: PasswordRepository,
    private val creditCardRepository: CreditCardRepository,
    private val authenticationManager: AuthenticationManager,
    private val googleAuthManager: GoogleAuthManager,
    private val backupJob: BackupJob,
    private val appState: AppState,
    private val logger: Logger,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ScreenModel {

    private val eventChannel = Channel<SecVaultEvent>(Channel.BUFFERED)

    private val _secVaultState = MutableStateFlow<UiState<Any>>(UiState.Idle)
    val secVaultState: StateFlow<UiState<Any>> = _secVaultState.asStateFlow()

    private val _menuItems = MutableStateFlow(DefaultMenuItem.entries.toList())
    val menuItems: StateFlow<List<DefaultMenuItem>> = _menuItems.asStateFlow()

    private val _selectedMenuItem = MutableStateFlow(_menuItems.value.first())
    val selectedMenuItem: StateFlow<DefaultMenuItem> = _selectedMenuItem.asStateFlow()

    private val _sortItems = MutableStateFlow(CredentialSort.entries.toList())
    val sortItems: StateFlow<List<CredentialSort>> = _sortItems.asStateFlow()

    private val _selectedSortItem = MutableStateFlow(sortItems.value.first())
    val selectedSortItem: StateFlow<CredentialSort> = _selectedSortItem.asStateFlow()

    private val _passwordItems = MutableStateFlow<List<PasswordSummary>>(emptyList())
    val passwordItems: StateFlow<List<PasswordSummary>> = _passwordItems.asStateFlow()

    private val _creditCardItems = MutableStateFlow<List<CreditCardSummary>>(emptyList())
    val creditCardItems: StateFlow<List<CreditCardSummary>> = _creditCardItems.asStateFlow()

    private val _selectedCredential = MutableStateFlow(SelectedCredential(null, null))
    val selectedCredential: StateFlow<SelectedCredential> = _selectedCredential.asStateFlow()

    init {
        handleEvents()
        initGoogleAuth()
    }

    fun selectMenuItem(item: DefaultMenuItem) {
        _selectedMenuItem.value = item
        this.onScreenShown()
    }

    fun selectSortItem(item: CredentialSort) {
        _selectedSortItem.value = item
    }

    fun clearError() {
        _secVaultState.value = UiState.Idle
    }

    fun loadCredentials() {
        screenModelScope.launch(dispatcher) {
            _secVaultState.value = UiState.Loading
            val id = selectedCredential.value.getId(_selectedMenuItem.value)
            _selectedCredential.value = SelectedCredential(null, null)
            id?.let { loadSelectedCredential(it) }

            when (_selectedMenuItem.value) {
                DefaultMenuItem.PASSWORDS -> loadPasswords(_selectedSortItem.value)
                DefaultMenuItem.CREDIT_CARD -> loadCreditCards(_selectedSortItem.value)
                DefaultMenuItem.NOTES -> TODO()
            }
        }
    }

    fun onScreenShown() {
        screenModelScope.launch(dispatcher) {
            eventChannel.send(SecVaultEvent.LoadCredentials)
        }
    }

    fun loadSelectedCredential(id: UUID) {
        screenModelScope.launch(dispatcher) {
            when (_selectedMenuItem.value) {
                DefaultMenuItem.PASSWORDS -> {
                    passwordRepository.findById(id).let { result ->
                        when (result) {
                            is Result.Error -> _secVaultState.value = UiState.Error(result.message)
                            is Result.Success<Password> -> _selectedCredential.value =
                                SelectedCredential(result.data, null)
                        }
                    }
                }

                DefaultMenuItem.CREDIT_CARD -> {
                    creditCardRepository.findById(id).let { result ->
                        when (result) {
                            is Result.Error -> _secVaultState.value = UiState.Error(result.message)
                            is Result.Success<CreditCard> -> _selectedCredential.value =
                                SelectedCredential(null, result.data)
                        }
                    }
                }

                DefaultMenuItem.NOTES -> TODO()
            }
        }
    }

    fun favorite(id: UUID) {
        screenModelScope.launch(dispatcher) {
            val result = when (selectedMenuItem.value) {
                DefaultMenuItem.PASSWORDS -> passwordRepository.favorite(id, appState.userName)
                DefaultMenuItem.CREDIT_CARD -> creditCardRepository.favorite(id, appState.userName)
                DefaultMenuItem.NOTES -> TODO()
            }

            when (result) {
                is Result.Error -> _secVaultState.value = UiState.Error(result.message)
                is Result.Success -> onScreenShown()
            }
        }
    }

    fun decryptPassword(text: String?): String {
        try {
            return appState.decryptPassword(text)
        } catch (e: Exception) {
            logger.error(e.message, e)
            return "FUCK U"
        }
    }

    suspend fun setMasterPassword(masterPassword: String): Result<Boolean> {
        return authenticationManager.setMasterPassword(masterPassword)
    }

    fun isMasterPasswordPresent(): Boolean {
        return appState.isMasterPasswordPresent()
    }

    suspend fun backupJob() {
        backupJob.start()
    }

    private suspend fun loadPasswords(sort: CredentialSort) {
        val criteria = CredentialSearchCriteria(SecurityContext.authenticatedUser?.id?.value, sort)
        _passwordItems.value = when (val passwords = passwordRepository.findSummaries(criteria)) {
            is Result.Success -> {
                _secVaultState.value = UiState.Success("Successfully loaded passwords")
                passwords.data
            }

            is Result.Error -> {
                _secVaultState.value = UiState.Error(passwords.message)
                emptyList()
            }
        }
    }

    private suspend fun loadCreditCards(sort: CredentialSort) {
        val criteria = CredentialSearchCriteria(SecurityContext.authenticatedUser?.id?.value, sort)
        _creditCardItems.value = when (val creditCards = creditCardRepository.findSummaries(criteria)) {
            is Result.Success -> {
                _secVaultState.value = UiState.Success("Successfully loaded credit cards")
                creditCards.data
            }

            is Result.Error -> {
                _secVaultState.value = UiState.Error(creditCards.message)
                emptyList()
            }
        }
    }

    private fun initGoogleAuth() {
        screenModelScope.launch(dispatcher) {
            googleAuthManager.authenticate()
            backupJob.start()
        }
    }

    private fun handleEvents() {
        screenModelScope.launch(dispatcher) {
            eventChannel.consumeAsFlow().collect { event ->
                when (event) {
                    is SecVaultEvent.LoadCredentials -> loadCredentials()
                }
            }
        }
    }

}