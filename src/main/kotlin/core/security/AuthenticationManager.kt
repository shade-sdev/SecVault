package core.security

import core.AppState
import kotlinx.coroutines.delay
import repository.Result
import repository.user.User
import repository.user.UserRepository

class AuthenticationManager(
    private val userRepository: UserRepository,
    private val jwtService: JwtService,
    private val appState: AppState
) {

    init {
        appState.userExists(userRepository.hasUser())
        loadUserFromToken()
    }

    suspend fun login(username: String, password: String): Result<User> {
        delay(200)
        return when (val result = userRepository.findByUsernameAndPassword(username, password)) {
            is Result.Success -> {
                appState.updateCurrentUser(result.data)
                TokenManager.saveToken(jwtService.generateToken(result.data))
                Result.Success(result.data)
            }

            is Result.Error -> result
        }
    }

    suspend fun register(username: String, email: String, password: String): Result<User> {
        delay(200)
        return when (val result = userRepository.createUser(username, email, password)) {
            is Result.Success -> {
                Result.Success(result.data)
            }

            is Result.Error -> result
        }
    }

    fun logout() {
        appState.clearCurrentUser()
    }

    private fun loadUserFromToken() {
        TokenManager.loadToken()?.let { token ->
            jwtService.validateToken(token)
        }?.let { id ->
            appState.updateCurrentUser(userRepository.findById(id))
        }
    }

}