package core.form.validation

val emailRule: ValidationRule = ValidationRule(
    condition = { email ->
        email.matches(Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"))
    },
    errorMessage = "Invalid email address"
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

fun lengthRule(field: String, length: Int): ValidationRule {
    return ValidationRule(
        condition = { field.length >= length },
        errorMessage = "$field must be $length characters or more"
    )
}

fun notNullRule(field: String): ValidationRule {
    return ValidationRule(
        condition = { it.isNotEmpty() },
        errorMessage = "$field must not be null"
    )
}