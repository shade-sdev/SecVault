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

    suspend fun resetPassword(email: String, newPassword: String, code: String): Result<User> {
        delay(200)

        val userResult = userRepository.findByEmail(email)
        if (userResult is Result.Error) {
            return userResult
        }

        val user = (userResult as Result.Success).data
        val otpVerification = twoFactorAuthenticationService.verifySecret(user.secretKey, code)
        if (otpVerification is Result.Error) {
            return Result.Error("Invalid One Time Password.")
        }

        val updateUserResult = userRepository.updateUser(user) {
            password = BCrypt.hashpw(newPassword, BCrypt.gensalt())
        }

        return when (updateUserResult) {
            is Result.Success -> Result.Success(updateUserResult.data)
            is Result.Error -> Result.Error(updateUserResult.message)
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