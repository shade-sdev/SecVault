package core.security

import core.AppState
import repository.Result
import repository.user.User
import repository.user.UserRepository

class AuthenticationManager(
    private val userRepository: UserRepository,
    private val appState: AppState
) {

    suspend fun login(username: String, password: String): Result<User> {
        return when (val result = userRepository.findByUsernameAndPassword(username, password)) {
            is Result.Success -> {
                appState.updateCurrentUser(result.data)
                Result.Success(result.data)
            }

            is Result.Error -> result
        }
    }

    fun logout() {
        appState.clearCurrentUser()
    }

}