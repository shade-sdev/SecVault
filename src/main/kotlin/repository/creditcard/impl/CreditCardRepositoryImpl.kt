package repository.creditcard.impl

import core.models.CredentialSort
import core.models.Result
import core.models.criteria.CredentialSearchCriteria
import core.models.dto.CreditCardDto
import kotlinx.coroutines.delay
import org.jetbrains.exposed.dao.load
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.Expression
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.andWhere
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.Logger
import repository.common.errors.DatabaseError
import repository.creditcard.CreditCard
import repository.creditcard.CreditCardRepository
import repository.creditcard.CreditCardTable
import repository.creditcard.projection.CreditCardSummary
import java.time.LocalDateTime
import java.util.*

class CreditCardRepositoryImpl(
    private val db: Database,
    private val logger: Logger
) : CreditCardRepository {

    override suspend fun findById(id: UUID): Result<CreditCard> {
        return try {
            return transaction(db) {
                CreditCard.findById(id)?.load(CreditCard::owner)
            }?.let { Result.Success(it) } ?: Result.Error("Credit Card not found")
        } catch (e: Exception) {
            logger.error(e.message, e)
            Result.Error(DatabaseError.fromException(e).extractMessage())
        }
    }

    override suspend fun findSummaries(searchCriteria: CredentialSearchCriteria): Result<List<CreditCardSummary>> {
        delay(550)
        return try {
            return transaction(db) {
                val query = CreditCardTable.select(
                    listOf(
                        CreditCardTable.id,
                        CreditCardTable.name,
                        CreditCardTable.number,
                        CreditCardTable.favorite
                    )
                )

                searchCriteria.userId?.let {
                    query.andWhere { CreditCardTable.user eq it }
                }

                query.orderBy(toSort(searchCriteria.sort), toOrder(searchCriteria.sort)).map { resultRow ->
                    CreditCardSummary(
                        id = resultRow[CreditCardTable.id].value,
                        name = resultRow[CreditCardTable.name].replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                        number = resultRow[CreditCardTable.number],
                        favorite = resultRow[CreditCardTable.favorite]
                    )
                }
            }.let { Result.Success(it) }
        } catch (e: Exception) {
            logger.error(e.message, e)
            Result.Error(DatabaseError.fromException(e).extractMessage())
        }
    }

    override suspend fun save(creditCardDto: CreditCardDto): Result<Boolean> {
        return try {
            return transaction(db) {
                CreditCard.new {
                    this.user = creditCardDto.user
                    this.expiryDate = creditCardDto.expiryDate
                    this.number = creditCardDto.number
                    this.cvc = creditCardDto.cvc
                    this.pin = creditCardDto.pin
                    this.notes = creditCardDto.notes
                    this.owner = creditCardDto.owner
                    this.name = creditCardDto.name.lowercase()
                    this.createdBy = creditCardDto.user.userName
                    this.lastUpdatedBy = creditCardDto.user.userName
                }
            }.let {
                Result.Success(true)
            }
        } catch (e: Exception) {
            logger.error(e.message, e)
            Result.Error(DatabaseError.fromException(e).extractMessage())
        }
    }

    override suspend fun update(id: UUID, user: String, creditCardDto: CreditCardDto): Result<Boolean> {
        return try {
            return transaction(db) {
                CreditCard.findById(id)?.let {
                    it.owner = creditCardDto.owner
                    it.name = creditCardDto.name
                    it.number = creditCardDto.number
                    it.cvc = creditCardDto.cvc
                    it.pin = creditCardDto.pin
                    it.notes = creditCardDto.notes
                    it.expiryDate = creditCardDto.expiryDate
                    it.lastUpdateDateTime = LocalDateTime.now()
                    it.lastUpdatedBy = user
                    it.version += 1
                }
            }.let {
                Result.Success(true)
            }
        } catch (e: Exception) {
            logger.error(e.message, e)
            Result.Error(DatabaseError.fromException(e).extractMessage())
        }
    }

    override suspend fun favorite(id: UUID, user: String): Result<Boolean> {
        return try {
            return transaction(db) {
                CreditCard.findById(id)?.let {
                    it.favorite = !it.favorite
                    it.lastUpdateDateTime = LocalDateTime.now()
                    it.lastUpdatedBy = user
                    it.version += 1
                }
            }.let {
                Result.Success(true)
            }
        } catch (e: Exception) {
            logger.error(e.message, e)
            Result.Error(DatabaseError.fromException(e).extractMessage())
        }
    }

    private fun toSort(sort: CredentialSort): Expression<*> {
        return when (sort) {
            CredentialSort.NAME -> CreditCardTable.name
            CredentialSort.CREATED -> CreditCardTable.createdBy
            CredentialSort.FAVORITE -> CreditCardTable.favorite
        }
    }

    private fun toOrder(sort: CredentialSort): SortOrder {
        return when (sort) {
            CredentialSort.NAME -> SortOrder.ASC
            CredentialSort.CREATED -> SortOrder.ASC
            CredentialSort.FAVORITE -> SortOrder.DESC
        }
    }

}