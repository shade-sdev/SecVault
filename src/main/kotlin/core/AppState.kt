package core

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import repository.user.User

class AppState {

    private var currentUser by mutableStateOf<User?>(null)
    private var userExists by mutableStateOf(false)

    fun updateCurrentUser(user: User?) {
        currentUser = user
    }

    fun userExists(exists: Boolean) {
        userExists = exists
    }

    fun userExist(): Boolean {
        return userExists
    }

    val getAuthenticatedUser: User
        get() = currentUser!!

    val isAuthenticated: Boolean
        get() = currentUser != null

    fun clearCurrentUser() {
        currentUser = null
    }
}