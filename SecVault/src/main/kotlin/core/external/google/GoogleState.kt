package core.external.google

import androidx.compose.ui.graphics.Color
import com.google.auth.oauth2.GoogleCredentials
import core.security.SecurityContext

/**
 * Represents the state of the Google application authentication process.
 */
sealed class GoogleAppState {

    /**
     * Retrieves the initial state of the Google application.
     *
     * @return The initial state, either Authenticated or NotAuthenticated.
     */
    companion object {
        fun getInitialAppState(): GoogleAppState {
            return SecurityContext.getGoogleCredential
                ?.let { Authenticated() }
                ?: NotAuthenticated()
        }
    }

    /**
     * State representing that the user is not authenticated.
     *
     * @property color The color associated with this state.
     */
    data class NotAuthenticated(val color: Color = Color.White) : GoogleAppState()

    /**
     * State representing that the user is in the process of authenticating.
     *
     * @property color The color associated with this state.
     */
    data class Authenticating(val color: Color = Color.Cyan) : GoogleAppState()

    /**
     * State representing that there was an error during authentication.
     *
     * @property message The error message.
     * @property color The color associated with this state.
     */
    data class AuthenticationError(
        val message: String,
        val color: Color = Color.Red
    ) : GoogleAppState()

    /**
     * State representing that the user is authenticated.
     *
     * @property color The color associated with this state.
     */
    data class Authenticated(
        val color: Color = Color.Green
    ) : GoogleAppState()

}

/**
 * Represents the state of the authentication process.
 */
sealed class AuthState {

    /**
     * State representing that the user is authorized.
     *
     * @property credential The credential associated with the authorized state.
     */
    data class Authorized(val credential: GoogleCredentials) : AuthState()

    /**
     * State representing that the authentication failed.
     *
     * @property error The error message.
     */
    data class Failed(val error: String) : AuthState()

}