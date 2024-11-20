package repository.creditcard

import core.models.Result
import core.models.criteria.CredentialSearchCriteria
import core.models.dto.CreditCardDto
import repository.creditcard.projection.CreditCardSummary

interface CreditCardRepository {

    suspend fun findSummaries(searchCriteria: CredentialSearchCriteria): Result<List<CreditCardSummary>>

    suspend fun save(creditCardDto: CreditCardDto): Result<Boolean>

}