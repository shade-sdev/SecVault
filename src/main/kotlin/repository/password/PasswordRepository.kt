package repository.password

import core.models.Result
import core.models.criteria.CredentialSearchCriteria
import core.models.dto.PasswordDto
import repository.password.projection.PasswordSummary
import java.util.UUID

interface PasswordRepository {

    suspend fun findById(id: UUID): Result<Password>

    suspend fun findSummaries(searchCriteria: CredentialSearchCriteria): Result<List<PasswordSummary>>

    suspend fun save(password: PasswordDto): Result<Boolean>

}