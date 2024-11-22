package repository.creditcard

import core.models.Result
import core.models.criteria.CredentialSearchCriteria
import core.models.dto.CreditCardDto
import repository.creditcard.projection.CreditCardSummary
import java.util.*

interface CreditCardRepository {

    suspend fun findById(id: UUID): Result<CreditCard>

    suspend fun findSummaries(searchCriteria: CredentialSearchCriteria): Result<List<CreditCardSummary>>

    suspend fun save(creditCardDto: CreditCardDto): Result<Boolean>

    suspend fun update(id: UUID, user: String, creditCardDto: CreditCardDto): Result<Boolean>

}