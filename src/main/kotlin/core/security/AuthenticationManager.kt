package core.security

import core.AppState
import kotlinx.coroutines.delay
import org.mindrot.jbcrypt.BCrypt
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
        return userRepository.findByUsername(username).let { result ->
            when (result) {
                is Result.Success -> {
                    if (BCrypt.checkpw(password, result.data.password)) {
                        result.data.apply {
                            appState.updateCurrentUser(this)
                            TokenManager.saveToken(jwtService.generateToken(this))
                        }.let { user ->
                            Result.Success(user)
                        }
                    } else {
                        Result.Error("Invalid Credentials")
                    }
                }

                is Result.Error -> result
            }
        }
    }


    suspend fun register(username: String, email: String, password: String): Result<User> {
        delay(200)
        return when (val result = userRepository.createUser(
            username,
            email,
            BCrypt.hashpw(password, BCrypt.gensalt())
        )) {
            is Result.Success -> {
                Result.Success(result.data)
            }

            is Result.Error -> result
        }
    }

    fun logout() {
        appState.clearCurrentUser()
        TokenManager.clearToken()
    }

    private fun loadUserFromToken() {
        TokenManager.loadToken()?.let {
            jwtService.validateToken(it)
        }?.let {
            appState.updateCurrentUser(userRepository.findById(it))
        }
    }

}