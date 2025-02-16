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
import core.security.SecurityContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.slf4j.Logger
import java.awt.Desktop
import java.io.InputStream
import java.io.InputStreamReader
import java.net.URI

class GoogleAuthManager(
    val logger: Logger
) {

    companion object Constants {

        val JSON_FACTORY: GsonFactory = GsonFactory.getDefaultInstance()

        val SCOPES = listOf(
            DriveScopes.DRIVE_FILE,
            PeopleScopes.PEOPLE_PROFILE,
            PeopleScopes.PEOPLE_EMAIL
        )

    }

    suspend fun authenticate(config: InputStream): AuthState = withContext(Dispatchers.IO) {
        try {
            val clientSecrets = GoogleClientSecrets.load(
                JSON_FACTORY,
                InputStreamReader(config)
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
        } catch (e: Exception) {
            logger.error(e.message, e)
            AuthState.Failed("Authentication error: ${e.message}")
        }
    }

    private suspend fun getUserInfo(credential: Credential): Person? = withContext(Dispatchers.IO) {
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