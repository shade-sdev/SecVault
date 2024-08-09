package viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import core.security.AuthenticationManager
import core.ui.UiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import repository.Result
import repository.user.User

class RegisterScreenModel(
    private val authenticationManager: AuthenticationManager, private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ScreenModel {

    private val _registerState = MutableStateFlow<UiState<User>>(UiState.Idle)
    val registerState: StateFlow<UiState<User>> = _registerState.asStateFlow()

    fun register(username: String, email: String, password: String) {
        screenModelScope.launch(dispatcher) {
            _registerState.value = UiState.Loading
            when (val result = authenticationManager.register(username, email, password)) {
                is Result.Success -> _registerState.value = UiState.Success(result.data)
                is Result.Error -> _registerState.value = UiState.Error(result.message)
            }
        }
    }

    fun clearError() {
        _registerState.value = UiState.Idle
    }

}