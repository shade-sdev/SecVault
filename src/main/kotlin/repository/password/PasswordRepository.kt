package repository.password

import core.models.Result
import core.models.criteria.PasswordSearchCriteria
import core.models.dto.PasswordDto
import repository.password.projection.PasswordSummary

interface PasswordRepository {

    suspend fun findSummaries(searchCriteria: PasswordSearchCriteria): Result<List<PasswordSummary>>

    suspend fun save(password: PasswordDto): Result<Boolean>

}