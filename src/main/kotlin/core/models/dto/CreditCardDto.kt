package core.models.dto

import repository.user.User

data class CreditCardDto(
    var user: User,
    var name: String,
    var owner: User,
    var number: String,
    var cvc: String?,
    var pin: String?,
    var expiryDate: String,
    var notes: String?
)
