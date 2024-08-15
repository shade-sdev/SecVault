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

data class MenuItem(val title: String, var selected: Boolean = false)