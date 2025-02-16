package core.external.google

import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport
import com.google.api.client.http.FileContent
import com.google.api.client.json.gson.GsonFactory
import com.google.api.services.drive.Drive
import java.io.File

object GoogleDriveService {

    fun create(credential: Credential): Drive {
        return Drive.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                credential)
                .setApplicationName("Drive Upload Desktop")
                .build()
    }

    fun uploadFile(drive: Drive, file: File): String {
        val fileMetadata = com.google.api.services.drive.model
                .File()
                .apply {
                    name = file.name
                }

        val mediaContent = FileContent("application/octet-stream", file)

        return drive.files()
                .create(fileMetadata, mediaContent)
                .setFields("id")
                .execute()
                .id
    }

}