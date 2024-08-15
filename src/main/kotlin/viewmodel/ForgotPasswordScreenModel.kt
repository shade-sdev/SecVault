package viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import core.Result
import core.security.AuthenticationManager
import core.ui.UiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import repository.user.User

class ForgotPasswordScreenModel(
    private val authenticationManager: AuthenticationManager,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ScreenModel {

    private val _forgotPasswordState = MutableStateFlow<UiState<User>>(UiState.Idle)
    val forgotPasswordState: StateFlow<UiState<User>> = _forgotPasswordState.asStateFlow()

    fun resetPassword(email: String, newPassword: String, otp: String) {
        screenModelScope.launch(dispatcher) {
            _forgotPasswordState.value = UiState.Loading
            when (val result = authenticationManager.resetPassword(email, newPassword, otp)) {
                is Result.Success -> _forgotPasswordState.value = UiState.Success(result.data)
                is Result.Error -> _forgotPasswordState.value = UiState.Error(result.message)
            }
        }
    }

    fun clearError() {
        _forgotPasswordState.value = UiState.Idle
    }

}