package core.security

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import repository.user.User

/**
 * SecurityContext is a singleton object that manages the authenticated user state.
 * It provides methods to set, get, and clear the authenticated user.
 */
object SecurityContext {

    private var currentUser by mutableStateOf<User?>(null)

    /**
     * Gets the authenticated user.
     * @return The authenticated user, or null if no user is authenticated.
     */
    val authenticatedUser: User?
        get() = currentUser

    /**
     * Checks if a user is authenticated.
     * @return True if a user is authenticated, false otherwise.
     */
    val isAuthenticated: Boolean
        get() = currentUser != null

    /**
     * Sets the authenticated user.
     * @param user The user to set as authenticated.
     */
    fun setAuthenticatedUser(user: User?) {
        currentUser = user
    }

    /**
     * Clears the authenticated user.
     */
    fun clearAuthenticatedUser() {
        currentUser = null
    }

}