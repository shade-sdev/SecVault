package core

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import cafe.adriel.voyager.core.screen.Screen
import repository.user.User
import ui.screens.LoginScreen
import ui.screens.RegisterScreen

class AppState {

    private var currentUser by mutableStateOf<User?>(null)
    private var userExists by mutableStateOf(false)

    fun updateCurrentUser(user: User?) {
        currentUser = user
    }

    fun userExists(exists: Boolean) {
        userExists = exists
    }

    val getAuthenticatedUser: User
        get() = currentUser!!

    val isAuthenticated: Boolean
        get() = currentUser != null

    fun clearCurrentUser() {
        currentUser = null
    }

    fun initialScreen(): Screen {
        return if (userExists) LoginScreen() else RegisterScreen()
    }
}