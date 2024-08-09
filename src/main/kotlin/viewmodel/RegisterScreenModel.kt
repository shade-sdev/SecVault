package viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import core.security.AuthenticationManager
import core.ui.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import repository.user.User

class RegisterScreenModel(private val authenticationManager: AuthenticationManager) : ScreenModel {

    private val _registerState = MutableStateFlow<UiState<User>>(UiState.Idle)
    val registerState: StateFlow<UiState<User>> = _registerState.asStateFlow()

    fun validate(userName: String, email: String, password: String) {

    }

    fun clearError() {
        _registerState.value = UiState.Idle
    }

}