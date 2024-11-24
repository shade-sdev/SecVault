package core.models

import repository.creditcard.CreditCard
import repository.password.Password
import java.util.*

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

    fun isSelected(): Boolean {
        return password != null || creditCard != null
    }

    fun isFavorite(): Boolean {
        return (password != null || creditCard != null) && (password?.favorite == true || creditCard?.favorite == true)
    }

    fun getId(): UUID? {
        return password?.id?.value ?: creditCard?.id?.value
    }

}