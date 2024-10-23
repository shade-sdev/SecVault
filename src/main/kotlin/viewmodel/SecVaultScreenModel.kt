package viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import core.AppState
import core.models.DefaultMenuItem
import core.models.PasswordSort
import core.models.Result
import core.models.UiState
import core.models.criteria.PasswordSearchCriteria
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import repository.password.PasswordRepository
import repository.password.projection.PasswordSummary

class SecVaultScreenModel(
    private val passwordRepository: PasswordRepository,
    private val appState: AppState,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ScreenModel {

    private val _secVaultState = MutableStateFlow<UiState<Any>>(UiState.Idle)
    val secVaultState: StateFlow<UiState<Any>> = _secVaultState.asStateFlow()

    private val _menuItems = MutableStateFlow(DefaultMenuItem.entries.toList())
    val menuItems: StateFlow<List<DefaultMenuItem>> = _menuItems.asStateFlow()

    private val _selectedMenuItem = MutableStateFlow(_menuItems.value.first())
    val selectedMenuItem: StateFlow<DefaultMenuItem> = _selectedMenuItem.asStateFlow()

    private val _sortItems = MutableStateFlow(PasswordSort.entries.toList())
    val sortItems: StateFlow<List<PasswordSort>> = _sortItems.asStateFlow()

    private val _selectedSortItem = MutableStateFlow(sortItems.value.first())
    val selectedSortItem: StateFlow<PasswordSort> = _selectedSortItem.asStateFlow()

    private val _passwordItems = MutableStateFlow<List<PasswordSummary>>(emptyList())
    val passwordItems: StateFlow<List<PasswordSummary>> = _passwordItems.asStateFlow()

    fun selectMenuItem(item: DefaultMenuItem) {
        _selectedMenuItem.value = item
    }

    fun selectSortItem(item: PasswordSort) {
        _selectedSortItem.value = item
    }

    fun clearError() {
        _secVaultState.value = UiState.Idle
    }

    fun init() {
        screenModelScope.launch(dispatcher) {
            _secVaultState.value = UiState.Loading
            loadPasswords(PasswordSort.NAME)
        }

        screenModelScope.launch(dispatcher) {
            _selectedMenuItem.collect { newMenuItem ->
                LoggerFactory.getLogger(SecVaultScreenModel::class.java).info(newMenuItem.value)
            }
        }

        screenModelScope.launch(dispatcher) {
            _selectedSortItem.collect { newFilterOption ->
                _secVaultState.value = UiState.Loading
                loadPasswords(newFilterOption)
            }
        }
    }

    private suspend fun loadPasswords(sort: PasswordSort) {
        val criteria = PasswordSearchCriteria(appState.getAuthenticatedUser?.id?.value, sort)
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

}