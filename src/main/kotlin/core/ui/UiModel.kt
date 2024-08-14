package core.ui

import androidx.compose.ui.graphics.Color
import java.util.*

enum class NotificationType(val color: Color) {
    ERROR(Color.Red),
    WARNING(Color(0xFFFFA000)),
    SUCCESS(Color.Green)
}

data class UserDto(val id: UUID, val userName: String, val email: String, val secretKey: String)