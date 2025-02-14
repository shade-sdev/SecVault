package core

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.screen.Screen
import core.security.MasterPasswordManager
import core.security.SecurityContext
import ui.screens.LoginScreen
import ui.screens.LoginSplashScreen
import ui.screens.RegisterScreen

/**
 * AppState class manages the state of the application, including the current user,
 * master password, and the initial screen to display.
 */
class AppState {

    private var userExists by mutableStateOf(false)
    private var masterPassword by mutableStateOf<CharArray?>(null)

    /**
     * Gets the username of the current user if they exist, otherwise returns "system".
     */
    val userName: String
        get() = SecurityContext.authenticatedUser?.userName.takeIf { userExists } ?: "system"

    /**
     * Sets whether a user exists.
     * @param exists Boolean indicating if a user exists.
     */
    fun userExists(exists: Boolean) {
        userExists = exists
    }

    /**
     * Determines the initial screen to display based on user existence and authentication status.
     * @return The initial screen to display.
     */
    fun initialScreen(): Screen = when {
        userExists && SecurityContext.isAuthenticated -> LoginSplashScreen()
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
     * Fetches the master password.
     * @return A copy of the master password.
     */
    private fun fetchMasterPassword(): CharArray? {
        return masterPassword?.copyOf()
    }

}