package core.external.google

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.DriveScopes
import com.google.api.services.people.v1.PeopleService
import com.google.api.services.people.v1.model.Person
import core.models.Result
import core.security.SecurityContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.Logger
import repository.google.GoogleDriveConfig
import repository.google.GoogleDriveConfigRepository
import java.awt.Desktop
import java.io.InputStreamReader
import java.net.URI

/**
 * Manager for handling Google authentication.
 *
 * @property googleDriveConfigRepository Repository for accessing Google Drive configuration.
 * @property logger Logger for logging messages.
 */
class GoogleAuthManager(
    private val googleDriveConfigRepository: GoogleDriveConfigRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val logger: Logger
) {

    /**
     * Companion object containing constants used in GoogleAuthManager.
     */
    companion object Constants {

        val JSON_FACTORY: GsonFactory = GsonFactory.getDefaultInstance()

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

                    val clientSecrets = GoogleClientSecrets.load(
                        JSON_FACTORY,
                        InputStreamReader(configFile.bytes.inputStream())
                    )

                    val flow = GoogleAuthorizationCodeFlow.Builder(
                        GoogleNetHttpTransport.newTrustedTransport(),
                        JSON_FACTORY,
                        clientSecrets,
                        SCOPES
                    )
                        .apply {
                            setDataStoreFactory(DatabaseDataStoreFactory())
                            accessType = "offline"
                            approvalPrompt = "force"
                        }
                        .build()

                    val userId = SecurityContext.getUserId.toString()
                    var credential = flow.loadCredential(userId)

                    if (credential == null) {
                        val receiver = LocalServerReceiver.Builder()
                            .setPort(8888)
                            .setCallbackPath("/oauth2callback")
                            .build()

                        try {
                            val authorizationUrl = flow.newAuthorizationUrl()
                                .setRedirectUri(receiver.redirectUri)
                                .build()

                            Desktop.getDesktop()
                                .browse(URI(authorizationUrl))

                            val code = receiver.waitForCode()

                            receiver.stop()

                            val response = flow.newTokenRequest(code)
                                .setRedirectUri(receiver.redirectUri)
                                .execute()

                            credential = flow.createAndStoreCredential(
                                response,
                                userId
                            )
                        } finally {
                            receiver.stop()
                        }
                    }

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
    private suspend fun getUserInfo(credential: Credential): Person? = withContext(dispatcher) {
        try {
            val peopleService = PeopleService.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                credential
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