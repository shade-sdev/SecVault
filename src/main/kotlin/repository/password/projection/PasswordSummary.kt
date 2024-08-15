package repository.password.projection

import java.util.*

data class PasswordSummary(
    val id: UUID,
    val name: String,
    val username: String?,
    val email: String?,
    val favorite: Boolean
)
