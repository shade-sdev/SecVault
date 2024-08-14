package core.security

import core.AppState
import core.Result
import kotlinx.coroutines.delay
import org.mindrot.jbcrypt.BCrypt
import repository.user.User
import repository.user.UserRepository
import repository.user.projection.UserSummary

class AuthenticationManager(
    private val userRepository: UserRepository,
    private val jwtService: JwtService,
    private val twoFactorAuthenticationService: TwoFactorAuthenticationService,
    private val appState: AppState
) {

    init {
        appState.userExists(userRepository.hasUser())
        loadUserFromToken()
    }

    suspend fun login(username: String, password: String): Result<User> {
        delay(200)
        return userRepository.findByUsername(username).let { result ->
            if (result is Result.Success && BCrypt.checkpw(password, result.data.password)) {
                result.data.also {
                    appState.updateCurrentUser(it)
                    TokenManager.saveToken(jwtService.generateToken(it))
                }.let { user ->
                    Result.Success(user)
                }
            } else {
                result
            }
        }
    }

    suspend fun register(username: String, email: String, password: String): Result<UserSummary> {
        delay(200)
        return when (val result = userRepository.createUser(
            username,
            email,
            BCrypt.hashpw(password, BCrypt.gensalt()),
            twoFactorAuthenticationService.generateSecretKey().base32Encoded
        )) {
            is Result.Success -> {
                Result.Success(result.data)
            }

            is Result.Error -> result
        }
    }

    fun openQRCode(secretKey: String, email: String) {
        twoFactorAuthenticationService.generateQRCodeImage(secretKey, email)
    }

    fun logout() {
        appState.clearCurrentUser()
        TokenManager.clearToken()
    }

    private fun loadUserFromToken() {
        TokenManager.loadToken()?.let { token ->
            jwtService.validateToken(token)?.let { userId ->
                appState.updateCurrentUser(userRepository.findById(userId))
            } ?: run {
                this.logout()
            }
        }
    }

}