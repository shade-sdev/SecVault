package repository.password

import core.models.PasswordSort
import core.models.Result
import repository.password.projection.PasswordSummary

interface PasswordRepository {

    suspend fun findSummaries(sort: PasswordSort): Result<List<PasswordSummary>>

}