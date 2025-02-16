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
                val existingConfig = GoogleDriveConfig.find { GoogleDriveConfigTable.id eq config.user.id }.singleOrNull()

                if (existingConfig != null) {
                    existingConfig.configFile = ExposedBlob(config.configFile)
                } else {
                    GoogleDriveConfig.new {
                        user = config.user
                        configFile = ExposedBlob(config.configFile)
                        credential = ExposedBlob(ByteArray(0))
                    }
                }
            }
            Result.Success(true)
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