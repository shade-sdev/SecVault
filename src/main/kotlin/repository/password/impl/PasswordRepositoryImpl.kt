package repository.password.impl

import core.models.Result
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.Logger
import repository.errors.DatabaseError
import repository.password.PasswordRepository
import repository.password.PasswordsTable
import repository.password.projection.PasswordSummary

class PasswordRepositoryImpl(
    private val db: Database,
    private val logger: Logger
) : PasswordRepository {

    override suspend fun findSummaries(): Result<List<PasswordSummary>> {
        return try {
            return transaction(db) {
                PasswordsTable.select(
                    listOf(
                        PasswordsTable.id,
                        PasswordsTable.name,
                        PasswordsTable.username,
                        PasswordsTable.email,
                        PasswordsTable.favorite
                    )
                ).map {
                    PasswordSummary(
                        id = it[PasswordsTable.id].value,
                        name = it[PasswordsTable.name],
                        username = it[PasswordsTable.username],
                        email = it[PasswordsTable.email],
                        favorite = it[PasswordsTable.favorite]
                    )
                }
            }.let { Result.Success(it) }
        } catch (e: Exception) {
            logger.error(e.message, e)
            Result.Error(DatabaseError.fromException(e).extractMessage())
        }
    }

}