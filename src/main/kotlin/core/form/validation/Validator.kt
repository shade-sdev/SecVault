package core.form.validation

class Validator {
    private val rules = mutableListOf<ValidationRule>()

    fun addRule(rule: ValidationRule): Validator {
        rules.add(rule)
        return this
    }

    fun validate(input: String): Pair<Boolean, String?> {
        return rules.firstOrNull { !it.condition(input) }
                       ?.let { false to it.errorMessage }
               ?: (true to null)
    }

}