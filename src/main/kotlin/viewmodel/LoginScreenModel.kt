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
import repository.user.User

class LoginScreenModel(
    private val authenticationManager: AuthenticationManager,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ScreenModel {

    private val _loginState = MutableStateFlow<UiState<User>>(UiState.Idle)
    val loginState: StateFlow<UiState<User>> = _loginState.asStateFlow()

    fun login(username: String, password: String, masterPassword: String) {
        screenModelScope.launch(dispatcher) {
            _loginState.value = UiState.Loading
            when (val result = authenticationManager.login(username, password, masterPassword)) {
                is Result.Success -> {
                    _loginState.value = UiState.Success(result.data)
                }
                is Result.Error -> _loginState.value = UiState.Error(result.message)
            }
        }
    }

    fun clearError() {
        _loginState.value = UiState.Idle
    }

}