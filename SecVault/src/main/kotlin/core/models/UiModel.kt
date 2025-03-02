package core.models

import androidx.compose.ui.graphics.Color
import java.util.*

/**
 * Enum class representing different types of notifications with associated colors.
 *
 * @property color The color associated with the notification type.
 */
enum class NotificationType(val color: Color) {
    ERROR(Color.Red),
    WARNING(Color(0xFFFFA000)),
    SUCCESS(Color.Green)
}

/**
 * Data class representing the display information for a credential.
 *
 * @property id The unique identifier of the credential.
 * @property title The title of the credential.
 * @property description The description of the credential.
 * @property favorite Indicates if the credential is marked as favorite.
 * @property isSelected Indicates if the credential is currently selected.
 */
data class CredentialDisplay(
    val id: UUID,
    val title: String,
    val description: String,
    val favorite: Boolean,
    val isSelected: Boolean
)

/**
 * Enum class representing different sorting options for credentials.
 *
 * @property value The string representation of the sorting option.
 */
enum class CredentialSort(val value: String) {
    NAME("Name"),
    FAVORITE("Favorite"),
    CREATED("Created"),
}

/**
 * Enum class representing different default menu items.
 *
 * @property value The string representation of the menu item.
 */
enum class DefaultMenuItem(val value: String) {
    PASSWORDS("Passwords"),
    CREDIT_CARD("Credit Cards"),
    NOTES("Notes")
}

/**
 * Enum class representing different types of forms.
 */
enum class FormType {
    CREATION,
    MODIFIATION
}

enum class PasswordCategory(val value: String) {
    SOCIAL_MEDIA("Social Media"),
    GAMING("Gaming"),
    E_COMMERCE("E Commerce"),
    BANKING("Banking"),
    EDUCATION("Education"),
    EMAIL("Email"),
}