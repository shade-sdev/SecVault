package core.external.google

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.ByteArrayContent
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import com.google.api.services.drive.model.Permission
import com.google.auth.http.HttpCredentialsAdapter
import com.google.auth.oauth2.GoogleCredentials
import core.models.dto.ExcelExportResult

/**
 * Service object for interacting with Google Drive.
 */
object GoogleDriveService {

    fun create(credential: GoogleCredentials): Drive {
        return Drive.Builder(
            GoogleNetHttpTransport.newTrustedTransport(),
            GsonFactory.getDefaultInstance(),
            HttpCredentialsAdapter(credential)
        )
            .setApplicationName("Drive Upload Desktop")
            .build()
    }

    fun createFolder(drive: Drive, folderName: String): String {
        val folderMetadata = com.google.api.services.drive.model.File().apply {
            name = folderName
            mimeType = "application/vnd.google-apps.folder"
        }

        val folder = drive.files().create(folderMetadata)
            .setFields("id")
            .execute()

        val permission = Permission().apply {
            type = "user"
            role = "writer"
            emailAddress = "form3pelican@gmail.com"
        }

        drive.permissions().create(folder.id, permission)
            .execute()

        return folder.id
    }

    fun uploadFile(drive: Drive, file: ExcelExportResult, folderId: String): String {
        val fileMetadata = com.google.api.services.drive.model.File()
            .apply {
                name = file.fileName
                parents = listOf(folderId)
            }

        val mediaContent = ByteArrayContent("application/octet-stream", file.data)

        return drive.files()
            .create(fileMetadata, mediaContent)
            .setFields("id")
            .execute()
            .id
    }

}