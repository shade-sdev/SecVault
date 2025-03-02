package core.security

import core.security.TokenManager.Constants.APP_FOLDER
import core.security.TokenManager.Constants.SYSTEM_PROPERTY
import core.security.TokenManager.Constants.TOKEN_FILE_NAME
import java.nio.file.Paths

/**
 * TokenManager is responsible for managing the security token.
 * It provides functionalities to save, load, and clear the token.
 */
object TokenManager {

    /**
     * Constants used by the TokenManager.
     */
    object Constants {
        const val SYSTEM_PROPERTY = "user.home"
        const val APP_FOLDER = ".secvault"
        const val TOKEN_FILE_NAME = "token"
    }

    private val tokenFile = Paths.get(System.getProperty(SYSTEM_PROPERTY), APP_FOLDER, TOKEN_FILE_NAME).toFile()

    /**
     * Saves the given token to the token file.
     *
     * @param token The token to be saved.
     */
    fun saveToken(token: String) {
        tokenFile.parentFile.exists().let {
            if (!it) {
                tokenFile.parentFile.mkdirs()
            }
        }

        tokenFile.writeText(token)
    }

    /**
     * Loads the token from the token file.
     *
     * @return The token if it exists, otherwise null.
     */
    fun loadToken(): String? {
        return if (tokenFile.exists()) tokenFile.readText() else null
    }

    /**
     * Clears the token by deleting the token file.
     *
     * @return True if the token file was deleted, otherwise false.
     */
    fun clearToken(): Boolean {
        if (tokenFile.exists()) {
            return tokenFile.delete()
        }

        return false
    }

}