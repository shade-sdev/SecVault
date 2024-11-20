package core.models.criteria

import core.models.CredentialSort
import java.util.*

data class CredentialSearchCriteria(
    val userId: UUID?,
    val sort: CredentialSort
)
