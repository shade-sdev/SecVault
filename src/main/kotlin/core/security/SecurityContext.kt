package core.security

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.google.api.client.auth.oauth2.Credential
import com.google.api.services.people.v1.model.Person
import repository.user.User
import java.util.*

/**
 * SecurityContext is a singleton object that manages the authenticated user state.
 * It provides methods to set, get, and clear the authenticated user.
 */
object SecurityContext {

    private var currentUser by mutableStateOf<User?>(null)
    private var credential by mutableStateOf<Credential?>(null)
    private var person by mutableStateOf<Person?>(null)

    /**
     * Gets the authenticated user.
     * @return The authenticated user, or null if no user is authenticated.
     */
    val authenticatedUser: User?
        get() = this.currentUser

    val getUserId: UUID?
        get() = this.currentUser?.id?.value

    val getGoogleCredential: Credential?
        get() = this.credential

    val getGooglePerson: Person?
        get() = this.person

    val getGooglePersonDisplayName: String?
        get() = this.person?.names?.firstOrNull()?.displayName

    /**
     * Sets the authenticated user.
     * @param user The user to set as authenticated.
     */
    fun setAuthenticatedUser(user: User?) {
        this.currentUser = user
    }

    fun setGoogleCredential(credential: Credential) {
        this.credential = credential
    }

    fun setGooglePerson(googlePerson: Person?) {
        this.person = googlePerson
    }

    /**
     * Checks if a user is authenticated.
     * @return True if a user is authenticated, false otherwise.
     */
    val isAuthenticated: Boolean
        get() = this.currentUser != null

    /**
     * Clears the authenticated user.
     */
    fun clearAuthenticatedUser() {
        this.currentUser = null
    }

}