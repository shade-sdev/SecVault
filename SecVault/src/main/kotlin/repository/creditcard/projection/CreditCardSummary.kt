package repository.creditcard.projection

import java.util.*

data class CreditCardSummary(
    val id: UUID,
    val name: String,
    val number: String,
    val favorite: Boolean
)