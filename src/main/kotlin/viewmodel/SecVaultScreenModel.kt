package viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import core.models.MenuItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SecVaultScreenModel : ScreenModel {

    private val _menuItems = MutableStateFlow<List<MenuItem>>(emptyList())
    val menuItems: StateFlow<List<MenuItem>> = _menuItems.asStateFlow()

    init {
        screenModelScope.launch {
            _menuItems.value = listOf(
                MenuItem("Passwords", true),
                MenuItem("Notes", false)
            )
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

}