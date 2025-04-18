package core.external.google

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.DriveScopes
import com.google.api.services.people.v1.PeopleService
import com.google.api.services.people.v1.model.Person
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.GoogleCredentials
import core.models.Result
import core.security.SecurityContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.sqlite.util.Logger
import org.sqlite.util.LoggerFactory
import repository.google.GoogleDriveConfig
import repository.google.GoogleDriveConfigRepository

/**
 * Manager for handling Google authentication.
 *
 * @property googleDriveConfigRepository Repository for accessing Google Drive configuration.
 * @property logger Logger for logging messages.
 */
class GoogleAuthManager(
    private val googleDriveConfigRepository: GoogleDriveConfigRepository,
    private val logger: Logger = LoggerFactory.getLogger(GoogleAuthManager::class.java),
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
    ) {

    /**
     * Companion object containing constants used in GoogleAuthManager.
     */
    companion object Constants {

        val JSON_FACTORY: GsonFactory = GsonFactory.getDefaultInstance()
        val HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport()

        /**
         * List of scopes required for Google authentication.
         */
        val SCOPES = listOf(
            DriveScopes.DRIVE_FILE,
            DriveScopes.DRIVE,
            PeopleScopes.PEOPLE_PROFILE,
            PeopleScopes.PEOPLE_EMAIL
        )

    }

    /**
     * Authenticates the user with Google.
     *
     * @return The authentication state, either Authorized or Failed.
     */
    suspend fun authenticate(): AuthState = withContext(dispatcher) {
        try {
            when (val result = googleDriveConfigRepository.findByUserId(SecurityContext.getUserId!!)) {
                is Result.Error -> {
                    AuthState.Failed("Config not found")
                }

                is Result.Success<GoogleDriveConfig> -> {
                    val configFile = result.data.configFile

                    if (configFile == null || configFile.bytes.isEmpty()) {
                        return@withContext AuthState.Failed("Credential is empty or not available")
                    }

                    val credential = GoogleCredentials.fromStream(configFile.inputStream)
                        .createScoped(SCOPES)

                    if (credential != null) {
                        SecurityContext.setGoogleCredential(credential)
                        SecurityContext.setGooglePerson(getUserInfo(credential))
                        AuthState.Authorized(credential)
                    } else {
                        AuthState.Failed("Authentication error: Failed to obtain credentials")
                    }
                }
            }


        } catch (e: Exception) {
            logger.error(e.message, e)
            AuthState.Failed("Authentication error: ${e.message}")
        }
    }

    /**
     * Retrieves user information from Google People API.
     *
     * @param credential The credential used for authentication.
     * @return The user information as a Person object, or null if an error occurs.
     */
    private suspend fun getUserInfo(credential: GoogleCredentials): Person? = withContext(dispatcher) {
        try {
            val peopleService = PeopleService.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                HttpCredentialsAdapter(credential)
            )
                .setApplicationName("Drive Upload Desktop")
                .build()

            val profile = peopleService.people()
                .get("people/me")
                .setPersonFields("names,emailAddresses")
                .execute()

            profile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}