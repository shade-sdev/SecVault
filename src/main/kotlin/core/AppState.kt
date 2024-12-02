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

class AppState {

    private var currentUser by mutableStateOf<User?>(null)
    private var userExists by mutableStateOf(false)
    private var masterPassword by mutableStateOf<CharArray?>(null)

    val getAuthenticatedUser: User?
        get() = currentUser

    val userName: String
        get() = currentUser?.userName.takeIf { userExists } ?: "system"

    fun updateCurrentUser(user: User?) {
        currentUser = user
    }

    fun userExists(exists: Boolean) {
        userExists = exists
    }

    fun clearCurrentUser() {
        currentUser = null
    }

    fun initialScreen(): Screen = when {
        userExists && isAuthenticated -> LoginSplashScreen()
        userExists -> LoginScreen()
        else -> RegisterScreen()
    }

    fun initializeMasterPassword(password: CharArray) {
        clearMasterPassword()
        masterPassword = password.copyOf()
    }

    fun fetchMasterPassword(): CharArray? {
        return masterPassword?.copyOf()
    }

    fun clearMasterPassword() {
        masterPassword?.fill('\u0000')
        masterPassword = null
    }

    fun isMasterPasswordPresent(): Boolean {
        return masterPassword != null
    }

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


    private val isAuthenticated: Boolean
        get() = currentUser != null

}