package viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import core.AppState
import core.models.Result
import core.models.UiState
import core.models.dto.PasswordDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import repository.password.PasswordRepository
import repository.user.User
import repository.user.UserRepository

class PasswordMgntScreenModel(
    private val passwordRepository: PasswordRepository,
    private val userRepository: UserRepository,
    private val appState: AppState,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ScreenModel {

    private val _passwordState = MutableStateFlow<UiState<Boolean>>(UiState.Idle)
    val passwordState: StateFlow<UiState<Boolean>> = _passwordState.asStateFlow()

    fun savePassword(password: PasswordDto) {
        screenModelScope.launch(dispatcher) {
            _passwordState.value = UiState.Loading

            when (val result = passwordRepository.save(password)) {
                is Result.Success -> _passwordState.value = UiState.Success(result.data)
                is Result.Error -> _passwordState.value = UiState.Error(result.message)
            }
        }
    }

    fun fetchUsers(): List<User> {
        return userRepository.findAll();
    }

    fun getAuthenticatedUser(): User {
        return appState.getAuthenticatedUser!!
    }

    fun clearError() {
        _passwordState.value = UiState.Idle
    }

}