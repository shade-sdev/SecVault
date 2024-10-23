package core

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.screen.Screen
import repository.user.User
import ui.screens.LoginScreen
import ui.screens.LoginSplashScreen
import ui.screens.RegisterScreen

class AppState {

    private var currentUser by mutableStateOf<User?>(null)
    private var userExists by mutableStateOf(false)

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

    private val isAuthenticated: Boolean
        get() = currentUser != null

}