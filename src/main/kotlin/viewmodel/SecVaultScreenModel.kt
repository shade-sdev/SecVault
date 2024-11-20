package viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import core.AppState
import core.models.CredentialSort
import core.models.DefaultMenuItem
import core.models.Result
import core.models.UiState
import core.models.criteria.CredentialSearchCriteria
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch
import repository.creditcard.CreditCardRepository
import repository.creditcard.projection.CreditCardSummary
import repository.password.PasswordRepository
import repository.password.projection.PasswordSummary

class SecVaultScreenModel(
    private val passwordRepository: PasswordRepository,
    private val creditCardRepository: CreditCardRepository,
    private val appState: AppState,
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

    init {
        handleEvents()
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
            when (_selectedMenuItem.value) {
                DefaultMenuItem.PASSWORDS -> loadPasswords(_selectedSortItem.value)
                DefaultMenuItem.CREDIT_CARD -> loadCreditCards(_selectedSortItem.value)
                DefaultMenuItem.NOTES -> TODO()
            }
        }
    }

    fun onScreenShown() {
        screenModelScope.launch {
            eventChannel.send(SecVaultEvent.LoadCredentials)
        }
    }

    private suspend fun loadPasswords(sort: CredentialSort) {
        val criteria = CredentialSearchCriteria(appState.getAuthenticatedUser?.id?.value, sort)
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
        val criteria = CredentialSearchCriteria(appState.getAuthenticatedUser?.id?.value, sort)
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

    private fun handleEvents() {
        screenModelScope.launch {
            eventChannel.consumeAsFlow().collect { event ->
                when (event) {
                    is SecVaultEvent.LoadCredentials -> loadCredentials()
                }
            }
        }
    }

}