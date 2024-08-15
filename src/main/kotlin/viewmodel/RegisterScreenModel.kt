package viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import core.models.Result
import core.models.UiState
import core.security.AuthenticationManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import repository.user.projection.UserSummary

class RegisterScreenModel(
    private val authenticationManager: AuthenticationManager,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ScreenModel {

    private val _registerState = MutableStateFlow<UiState<UserSummary>>(UiState.Idle)
    val registerState: StateFlow<UiState<UserSummary>> = _registerState.asStateFlow()

    fun register(username: String, email: String, password: String) {
        screenModelScope.launch(dispatcher) {
            _registerState.value = UiState.Loading
            when (val result = authenticationManager.register(username, email, password)) {
                is Result.Success -> _registerState.value = UiState.Success(result.data)
                is Result.Error -> _registerState.value = UiState.Error(result.message)
            }
        }
    }

    fun openQRCode(user: UserSummary) {
        authenticationManager.openQRCode(user.secretKey, user.email)
    }

    fun clearError() {
        _registerState.value = UiState.Idle
    }

}