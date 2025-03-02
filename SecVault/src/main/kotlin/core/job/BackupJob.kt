package core.job

import core.AppState
import core.external.google.GoogleDriveService
import core.models.Result
import core.models.dto.ExcelExportResult
import core.models.dto.GoogleDriveConfigDto
import core.security.SecurityContext
import core.service.ExcelExportService
import core.service.TrayService
import org.sqlite.util.Logger
import org.sqlite.util.LoggerFactory
import repository.google.GoogleDriveConfig
import repository.google.GoogleDriveConfigRepository
import repository.user.UserRepository
import java.awt.TrayIcon
import java.time.LocalDateTime

/**
 * Job responsible for backing up data to Google Drive.
 *
 * @property googleDriveConfigRepository Repository for accessing Google Drive configuration.
 * @property exportService Service for exporting data to Excel.
 * @property appState Application state.
 * @property logger Logger for logging messages.
 */
class BackupJob(
    private val userRepository: UserRepository,
    private val googleDriveConfigRepository: GoogleDriveConfigRepository,
    private val exportService: ExcelExportService,
    private val appState: AppState,
    private val logger: Logger = LoggerFactory.getLogger(BackupJob::class.java)
) {

    /**
     * Starts the backup job.
     *
     * @return Result indicating success or failure of the backup operation.
     */
    suspend fun start(): Result<Boolean> {
        if (SecurityContext.getGoogleCredential == null || !appState.isMasterPasswordPresent()) {
            logger.error("Google Credential is not setup.", RuntimeException())
            return Result.Error("No google credential.")
        }

        if (!userRepository.hasUserData(SecurityContext.getUserId!!)) {
            logger.warn("No data skipping backup job.")
            return Result.Error("No data skipping backup job.")
        }

        when (val configResult = googleDriveConfigRepository.findByUserId(SecurityContext.getUserId!!)) {
            is Result.Error -> {
                logger.error("Config not found skipping backup job", RuntimeException())
                return Result.Error("Skipping backup job.")
            }

            is Result.Success<GoogleDriveConfig> -> {
                TrayService.showNotification("SecVault", "Backup in progress...", TrayIcon.MessageType.INFO)
                val drive = GoogleDriveService.create(SecurityContext.getGoogleCredential!!)
                var folderId = configResult.data.folderId

                if (folderId == null) {
                    folderId = GoogleDriveService.createFolder(drive, "SecVault")
                }

                when (val exportResult = exportService.exportExcel()) {
                    is Result.Error -> {
                        logger.error("Failed to export ${exportResult.message}", RuntimeException())
                        return Result.Error(exportResult.message)
                    }

                    is Result.Success<ExcelExportResult> -> {
                        val fileId = GoogleDriveService.uploadFile(drive, exportResult.data, folderId)
                        googleDriveConfigRepository.save(
                            GoogleDriveConfigDto(
                                user = SecurityContext.authenticatedUser!!,
                                configFile = configResult.data.configFile!!.bytes,
                                storedCredential = configResult.data.credential?.bytes,
                                folderId = folderId,
                                lastBackupId = fileId,
                                lastBackupDate = LocalDateTime.now(),
                            )
                        )
                        TrayService.showNotification("SecVault", "Backup completed", TrayIcon.MessageType.INFO)
                        return Result.Success(true)
                    }
                }

            }
        }
    }

}