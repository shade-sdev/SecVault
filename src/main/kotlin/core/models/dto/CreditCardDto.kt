package core.models.dto

import repository.user.User

data class CreditCardDto(
    var user: User,
    var name: String,
    var owner: User,
    var number: String,
    var cvc: Int?,
    var pin: Int?,
    var expiryDate: String,
    var notes: String?
)
