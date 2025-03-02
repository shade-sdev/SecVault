package repository.user.projection

import java.util.*

data class UserSummary(
    val id: UUID,
    val username: String,
    val email: String,
    val secretKey: String
)
