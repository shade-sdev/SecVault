package core.security

import core.security.TokenManager.Constants.APP_FOLDER
import core.security.TokenManager.Constants.SYSTEM_PROPERTY
import core.security.TokenManager.Constants.TOKEN_FILE_NAME
import java.nio.file.Paths

object TokenManager {

    object Constants {
        const val SYSTEM_PROPERTY = "user.home"
        const val APP_FOLDER = ".secvault"
        const val TOKEN_FILE_NAME = "token"
    }

    private val tokenFile = Paths.get(System.getProperty(SYSTEM_PROPERTY), APP_FOLDER, TOKEN_FILE_NAME).toFile()

    fun saveToken(token: String) {
        tokenFile.parentFile.exists().let {
            if (!it) {
                tokenFile.parentFile.mkdirs()
            }
        }

        tokenFile.writeText(token)
    }

    fun loadToken(): String? {
        return if (tokenFile.exists()) tokenFile.readText() else null
    }

    fun clearToken(): Boolean {
        if (tokenFile.exists()) {
            return tokenFile.delete()
        }

        return false
    }

}