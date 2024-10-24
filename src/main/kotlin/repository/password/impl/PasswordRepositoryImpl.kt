package repository.password.impl

import core.models.PasswordSort
import core.models.Result
import core.models.criteria.PasswordSearchCriteria
import core.models.dto.PasswordDto
import kotlinx.coroutines.delay
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Expression
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.Logger
import repository.common.errors.DatabaseError
import repository.password.Password
import repository.password.PasswordRepository
import repository.password.PasswordsTable
import repository.password.projection.PasswordSummary
import java.util.*

class PasswordRepositoryImpl(
    private val db: Database,
    private val logger: Logger
) : PasswordRepository {

    override suspend fun findSummaries(searchCriteria: PasswordSearchCriteria): Result<List<PasswordSummary>> {
        delay(550)
        return try {
            return transaction(db) {

                val query = PasswordsTable.select(
                    listOf(
                        PasswordsTable.id,
                        PasswordsTable.name,
                        PasswordsTable.username,
                        PasswordsTable.email,
                        PasswordsTable.favorite
                    )
                )

                searchCriteria.userId?.let {
                    query.andWhere { PasswordsTable.user eq it }
                }

                query.orderBy(toSort(searchCriteria.sort), toOrder(searchCriteria.sort)).map { resultRow ->
                    PasswordSummary(
                        id = resultRow[PasswordsTable.id].value,
                        name = resultRow[PasswordsTable.name].replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                        username = resultRow[PasswordsTable.username],
                        email = resultRow[PasswordsTable.email],
                        favorite = resultRow[PasswordsTable.favorite]
                    )
                }
            }.let { Result.Success(it) }
        } catch (e: Exception) {
            logger.error(e.message, e)
            Result.Error(DatabaseError.fromException(e).extractMessage())
        }
    }

    override suspend fun save(password: PasswordDto): Result<Boolean> {
        return try {
            return transaction(db) {
                Password.new {
                    this.user = password.user
                    this.username = password.userName
                    this.email = password.email
                    this.password = password.password
                    this.name = password.name.lowercase()
                    this.website = password.website
                    this.websiteIcon = password.icon
                    this.createdBy = password.user.userName
                    this.lastUpdatedBy = password.user.userName
                }
            }.let {
                Result.Success(true)
            }
        } catch (e: Exception) {
            logger.error(e.message, e)
            Result.Error(DatabaseError.fromException(e).extractMessage())
        }
    }

    private fun toSort(sort: PasswordSort): Expression<*> {
        return when (sort) {
            PasswordSort.NAME -> PasswordsTable.name
            PasswordSort.CREATED -> PasswordsTable.createdBy
            PasswordSort.FAVORITE -> PasswordsTable.favorite
        }
    }

    private fun toOrder(sort: PasswordSort): SortOrder {
        return when (sort) {
            PasswordSort.NAME -> SortOrder.ASC
            PasswordSort.CREATED -> SortOrder.ASC
            PasswordSort.FAVORITE -> SortOrder.DESC
        }
    }

}