package core.models

import androidx.compose.ui.graphics.Color
import java.util.*

enum class NotificationType(val color: Color) {
    ERROR(Color.Red),
    WARNING(Color(0xFFFFA000)),
    SUCCESS(Color.Green)
}

data class CredentialDisplay(
    val id: UUID,
    val title: String,
    val description: String,
    val favorite: Boolean,
    val isSelected: Boolean
)

enum class CredentialSort(val value: String) {
    NAME("Name"),
    FAVORITE("Favorite"),
    CREATED("Created"),
}

enum class DefaultMenuItem(val value: String) {
    PASSWORDS("Passwords"),
    CREDIT_CARD("Credit Cards"),
    NOTES("Notes")
}

enum class FormType {
    CREATION,
    MODIFIATION
}