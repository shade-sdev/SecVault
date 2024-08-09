package core.form.validation

data class ValidationRule(
    val condition: (String) -> Boolean,
    val errorMessage: String
)
