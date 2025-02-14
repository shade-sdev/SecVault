package repository.google

import core.models.Result
import core.models.dto.GoogleDriveConfigDto
import java.util.*

interface GoogleDriveConfigRepository {

    suspend fun save(config: GoogleDriveConfigDto): Result<Boolean>

    suspend fun findByUserId(userId: UUID): Result<GoogleDriveConfig>

}