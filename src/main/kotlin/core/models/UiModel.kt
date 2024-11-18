package core.models

import androidx.compose.ui.graphics.Color

enum class NotificationType(val color: Color) {
    ERROR(Color.Red),
    WARNING(Color(0xFFFFA000)),
    SUCCESS(Color.Green)
}

enum class PasswordSort(val value: String) {
    NAME("Name"),
    FAVORITE("Favorite"),
    CREATED("Created"),
}

enum class DefaultMenuItem(val value: String) {
    PASSWORDS("Passwords"),
    CREDIT_CARD("Credit Cards"),
    NOTES("Notes")
}