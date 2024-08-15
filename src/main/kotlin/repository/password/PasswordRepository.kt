package repository.password

import core.models.Result
import repository.password.projection.PasswordSummary

interface PasswordRepository {

    suspend fun findSummaries(): Result<List<PasswordSummary>>

}