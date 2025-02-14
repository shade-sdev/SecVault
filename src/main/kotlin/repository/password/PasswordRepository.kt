package repository.password

import core.models.Result
import core.models.criteria.CredentialSearchCriteria
import core.models.dto.PasswordDto
import repository.password.projection.PasswordSummary
import java.util.*

interface PasswordRepository {

    suspend fun findById(id: UUID): Result<Password>

    suspend fun findSummaries(searchCriteria: CredentialSearchCriteria): Result<List<PasswordSummary>>

    suspend fun save(password: PasswordDto): Result<Boolean>

    suspend fun update(id: UUID, user: String, password: PasswordDto): Result<Boolean>

    suspend fun favorite(id: UUID, user: String): Result<Boolean>

    suspend fun findFirstEncryptedField(userId: UUID, sampleText: String): Result<String>

}