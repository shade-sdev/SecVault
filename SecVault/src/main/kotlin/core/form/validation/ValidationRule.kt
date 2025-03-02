package core.form.validation

/**
 * Represents a validation rule for form input.
 *
 * @property condition A lambda function that takes a String and returns a Boolean indicating if the condition is met.
 * @property errorMessage The error message to be displayed if the condition is not met.
 */
data class ValidationRule(
    val condition: (String) -> Boolean,
    val errorMessage: String
)