package core.models.criteria

import core.models.PasswordSort
import java.util.*

data class PasswordSearchCriteria(
    val userId: UUID?,
    val sort: PasswordSort
)
