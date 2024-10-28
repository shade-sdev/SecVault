package core.form.validation

import core.form.FormField
import java.util.*

val emailRule: ValidationRule = ValidationRule(
    condition = { email ->
        email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))
    },
    errorMessage = "Invalid email address"
)

val emailIfNotNullRule: ValidationRule = ValidationRule(
    condition = { email ->
        if (email.isNotEmpty()) {
            emailRule.condition.invoke(email)
        } else {
            true
        }
    },
    errorMessage = "Invalid email address"
)

val urlRule: ValidationRule = ValidationRule(
    condition = { url ->
        url.isEmpty() || url.matches(Regex("^https?://(?:www\\.)?[-a-zA-Z0-9@:%._+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b[-a-zA-Z0-9()@:%_+.~#?&/=]*$"))
    },
    errorMessage = "Invalid URL"
)

val passwordRule: ValidationRule = ValidationRule(
    condition = { password ->
        password.length >= 8 &&
                password.any { it.isDigit() } &&
                password.any { it.isUpperCase() } &&
                password.any { it.isLowerCase() } &&
                password.any { it in "!@#$%^&*()-_=+[]{}|;:'\",.<>?/~`" }
    },
    errorMessage = "Password must be 8+ chars, with a number, symbol, upper & lower case."
)

val creditCardRule: ValidationRule = ValidationRule(
    condition = { card ->
        card.matches(Regex("^4[0-9]{12}(?:[0-9]{3})?$"))
                || card.matches(Regex("^5[1-5][0-9]{14}$"))
    },
    errorMessage = "Invalid Credit Card Number"
)

val creditCardPinRule: ValidationRule = ValidationRule(
    condition = { card ->
        card.matches(Regex("^[0-9]{3,4}$"))
    },
    errorMessage = "Invalid Credit Card PIN"
)

val creditCardExpiryDateRule: ValidationRule = ValidationRule(
    condition = { card ->
        card.matches(Regex("^(0[1-9]|1[0-2])/([0-9]{2})$"))
    },
    errorMessage = "Invalid Credit Card Expiry Date"
)

fun lengthRule(field: String, length: Int): ValidationRule {
    return ValidationRule(
        condition = { field.length >= length },
        errorMessage = "$field must be $length characters or more"
    )
}

fun minMaxRule(field: String, min: Int, max: Int): ValidationRule {
    val errorMessage = when {
        min == max -> "$field must be $max characters"
        else -> "$field must be between $min and $max characters"
    }

    return ValidationRule(
        condition = { it.length in min..max },
        errorMessage = errorMessage
    )
}

fun instanceRule(field: String, type: Class<*>): ValidationRule {
    return ValidationRule(
        condition = {
            val value = when (type) {
                Integer::class.java -> it.toIntOrNull()
                Double::class.java -> it.toDoubleOrNull()
                Long::class.java -> it.toLongOrNull()
                Boolean::class.java -> it.toBoolean()
                String::class.java -> it
                else -> null
            }
            Objects.nonNull(value) && type.isInstance(value!!)
        },
        errorMessage = "$field must be of type ${type.simpleName}"
    )
}

fun notNullRule(field: String): ValidationRule {
    return ValidationRule(
        condition = { it.isNotEmpty() },
        errorMessage = "$field must not be null"
    )
}

fun conditionalRule(comparingField: () -> FormField, rules: List<ValidationRule>): ValidationRule {
    return ValidationRule(
        condition = { input ->
            if (comparingField().value.value.isNotEmpty()) {
                true
            } else {
                rules.all { rule -> rule.condition(input) }
            }
        },
        errorMessage = rules.firstOrNull()?.errorMessage ?: "Invalid input"
    )
}