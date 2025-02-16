package core.external.google

import androidx.compose.ui.graphics.Color
import com.google.api.client.auth.oauth2.Credential
import core.security.SecurityContext

sealed class GoogleAppState {

    companion object {
        fun getInitialAppState(): GoogleAppState {
            return SecurityContext.getGoogleCredential
                    ?.let { Authenticated() }
                ?: NotAuthenticated()
        }
    }

    data class NotAuthenticated(val color: Color = Color.White) : GoogleAppState()
    data class Authenticating(val color: Color = Color.Cyan) : GoogleAppState()

    data class AuthenticationError(
        val message: String,
        val color: Color = Color.Red
    ) : GoogleAppState()

    data class Authenticated(
        val color: Color = Color.Green
    ) : GoogleAppState()

}

sealed class UploadState {
    data object NotStarted : UploadState()
    data object InProgress : UploadState()
    data class Success(val fileId: String) : UploadState()
    data class Error(val message: String) : UploadState()
}

sealed class AuthState {
    data class Authorized(val credential: Credential) : AuthState()
    data class Failed(val error: String) : AuthState()
}