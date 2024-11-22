package core.models

import androidx.compose.ui.graphics.Color
import repository.creditcard.CreditCard
import repository.password.Password
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
    val favorite: Boolean
)

data class SelectedCredential(
    val password: Password?,
    val creditCard: CreditCard?
) {

    fun getTitle(type: DefaultMenuItem): String {
        return when(type) {
            DefaultMenuItem.PASSWORDS -> {
                password?.name?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } ?: ""
            }

            DefaultMenuItem.CREDIT_CARD -> {
                creditCard?.name?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() } ?: ""
            }

            DefaultMenuItem.NOTES -> TODO()
        }
    }

    fun getDescription(type: DefaultMenuItem): String {
        return when(type) {
            DefaultMenuItem.PASSWORDS -> {
                password?.email?.takeIf { it.isNotEmpty() } ?: password?.username ?: ""
            }

            DefaultMenuItem.CREDIT_CARD -> {
                creditCard?.number ?: ""
            }

            DefaultMenuItem.NOTES -> TODO()
        }
    }

}

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