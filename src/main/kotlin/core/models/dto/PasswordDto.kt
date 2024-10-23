package core.models.dto

import repository.user.User

data class PasswordDto(
    var userName: String?,
    var email: String?,
    var password: String,
    var name: String,
    var website: String?,
    var icon: String?,
    var user: User
)