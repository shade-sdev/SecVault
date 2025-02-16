package repository.google.impl

import core.models.Result
import core.models.dto.GoogleDriveConfigDto
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.statements.api.ExposedBlob
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.Logger
import repository.common.errors.DatabaseError
import repository.google.GoogleDriveConfig
import repository.google.GoogleDriveConfigRepository
import repository.google.GoogleDriveConfigTable
import java.util.*

class GoogleDriveConfigRepositoryImpl(
    private val db: Database,
    private val logger: Logger
) : GoogleDriveConfigRepository {

    override suspend fun save(config: GoogleDriveConfigDto): Result<Boolean> {
        return try {
            transaction(db) {
                val existingConfig =
                    GoogleDriveConfig.find { GoogleDriveConfigTable.id eq config.user.id }.singleOrNull()

                if (existingConfig != null) {
                    existingConfig.configFile = ExposedBlob(config.configFile)
                    existingConfig.credential = config.storedCredential?.let { ExposedBlob(it) }
                    existingConfig.folderId = config.folderId
                    existingConfig.lastBackupId = config.lastBackupId
                    existingConfig.lastBackupDate = config.lastBackupDate
                } else {
                    GoogleDriveConfig.new {
                        user = config.user
                        configFile = ExposedBlob(config.configFile)
                        credential = config.storedCredential?.let { ExposedBlob(it) }
                        folderId = config.folderId
                        lastBackupId = config.lastBackupId
                        lastBackupDate = config.lastBackupDate
                    }
                }
            }
            Result.Success(true)
        } catch (e: Exception) {
            logger.error(e.message, e)
            Result.Error(DatabaseError.fromException(e).extractMessage())
        }
    }

    override suspend fun resetFolder(userId: UUID): Result<Boolean> {
        return try {
            transaction(db) {
                val config = GoogleDriveConfig.find { GoogleDriveConfigTable.id eq userId }.firstOrNull()

                config?.apply {
                    folderId = null
                    lastBackupId = null
                    lastBackupDate = null
                }

                config?.let { Result.Success(true) } ?: Result.Error("Config not found")
            }
        } catch (e: Exception) {
            logger.error(e.message, e)
            Result.Error(DatabaseError.fromException(e).extractMessage())
        }
    }

    override suspend fun findByUserId(userId: UUID): Result<GoogleDriveConfig> {
        return try {
            return transaction(db) {
                GoogleDriveConfig.find { GoogleDriveConfigTable.id eq userId }.singleOrNull()
            }?.let { Result.Success(it) } ?: Result.Error("Config file not found")
        } catch (e: Exception) {
            logger.error(e.message, e)
            Result.Error(DatabaseError.fromException(e).extractMessage())
        }
    }

}