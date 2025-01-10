package core

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.screen.Screen
import core.security.MasterPasswordManager
import repository.user.User
import ui.screens.LoginScreen
import ui.screens.LoginSplashScreen
import ui.screens.RegisterScreen

/**
 * AppState class manages the state of the application, including the current user,
 * master password, and the initial screen to display.
 */
class AppState {

    private var currentUser by mutableStateOf<User?>(null)
    private var userExists by mutableStateOf(false)
    private var masterPassword by mutableStateOf<CharArray?>(null)

    /**
     * Gets the authenticated user.
     */
    val getAuthenticatedUser: User?
        get() = currentUser

    /**
     * Gets the username of the current user if they exist, otherwise returns "system".
     */
    val userName: String
        get() = currentUser?.userName.takeIf { userExists } ?: "system"

    /**
     * Updates the current user.
     * @param user The user to set as the current user.
     */
    fun updateCurrentUser(user: User?) {
        currentUser = user
    }

    /**
     * Sets whether a user exists.
     * @param exists Boolean indicating if a user exists.
     */
    fun userExists(exists: Boolean) {
        userExists = exists
    }

    /**
     * Clears the current user.
     */
    fun clearCurrentUser() {
        currentUser = null
    }

    /**
     * Determines the initial screen to display based on user existence and authentication status.
     * @return The initial screen to display.
     */
    fun initialScreen(): Screen = when {
        userExists && isAuthenticated -> LoginSplashScreen()
        userExists -> LoginScreen()
        else -> RegisterScreen()
    }

    /**
     * Initializes the master password.
     * @param password The master password to set.
     */
    fun initializeMasterPassword(password: CharArray) {
        clearMasterPassword()
        masterPassword = password.copyOf()
    }

    /**
     * Fetches the master password.
     * @return A copy of the master password.
     */
    fun fetchMasterPassword(): CharArray? {
        return masterPassword?.copyOf()
    }

    /**
     * Clears the master password.
     */
    fun clearMasterPassword() {
        masterPassword?.fill('\u0000')
        masterPassword = null
    }

    /**
     * Checks if the master password is present.
     * @return True if the master password is present, false otherwise.
     */
    fun isMasterPasswordPresent(): Boolean {
        return masterPassword != null
    }

    /**
     * Encrypts a string using the master password.
     * @param text The string to encrypt.
     * @return The encrypted string.
     */
    fun encryptString(text: String?): String {
        return text?.let {
            MasterPasswordManager.encryptString(
                it,
                MasterPasswordManager.getKey(
                    MasterPasswordManager.convertToUnsecureString(this.fetchMasterPassword()!!)
                )
            )
        } ?: ""
    }

    /**
     * Decrypts a string using the master password.
     * @param text The string to decrypt.
     * @return The decrypted string.
     */
    fun decryptPassword(text: String?): String {
        return text?.let {
            MasterPasswordManager.decryptString(
                it,
                MasterPasswordManager.getKey(
                    MasterPasswordManager.convertToUnsecureString(this.fetchMasterPassword()!!)
                )
            )
        } ?: ""
    }

    /**
     * Checks if the user is authenticated.
     * @return True if the user is authenticated, false otherwise.
     */
    private val isAuthenticated: Boolean
        get() = currentUser != null

}