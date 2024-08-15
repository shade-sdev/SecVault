package viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import core.models.DefaultMenuItem
import core.models.MenuItem
import core.models.Result
import core.models.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import repository.password.PasswordRepository
import repository.password.projection.PasswordSummary

class SecVaultScreenModel(private val passwordRepository: PasswordRepository) : ScreenModel {

    private val _secVaultState = MutableStateFlow<UiState<Any>>(UiState.Idle)
    val secVaultState: StateFlow<UiState<Any>> = _secVaultState.asStateFlow()

    private val _menuItems = MutableStateFlow<List<MenuItem>>(emptyList())
    val menuItems: StateFlow<List<MenuItem>> = _menuItems.asStateFlow()

    private val _passwordItems = MutableStateFlow<List<PasswordSummary>>(emptyList())
    val passwordItems: StateFlow<List<PasswordSummary>> = _passwordItems.asStateFlow()

    private val _filterItems = MutableStateFlow(DefaultMenuItem.entries.toList())
    val filterItems: StateFlow<List<DefaultMenuItem>> = _filterItems.asStateFlow()

    private val _selectedFilterOption = MutableStateFlow(filterItems.value.first())
    val selectedFilterOption: StateFlow<DefaultMenuItem> = _selectedFilterOption.asStateFlow()

    init {
        screenModelScope.launch {
            _secVaultState.value = UiState.Loading
            _menuItems.value = listOf(
                MenuItem("Passwords", true),
                MenuItem("Notes", false)
            )

            _passwordItems.value = when (val passwords = passwordRepository.findSummaries()) {
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

    fun selectMenuItem(index: Int) {
        screenModelScope.launch {
            val updatedItems = _menuItems.value.mapIndexed { i, menuItem ->
                menuItem.copy(selected = i == index)
            }
            _menuItems.value = updatedItems
        }
    }

    fun selectFilterItem(item: DefaultMenuItem) {
        _selectedFilterOption.value = item
    }

    fun clearError() {
        _secVaultState.value = UiState.Idle
    }

}