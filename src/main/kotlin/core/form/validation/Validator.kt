package core.form.validation

/**
 * Validator class for managing and applying validation rules to input strings.
 */
class Validator {

    private val rules = mutableListOf<ValidationRule>()

    fun addRule(rule: ValidationRule): Validator {
        this.rules.add(rule)
        return this
    }

    fun addRules(additionalRules: List<ValidationRule>): Validator {
        this.rules.addAll(additionalRules)
        return this
    }

    fun validate(input: String): Pair<Boolean, String?> {
        return rules.firstOrNull { !it.condition(input) }
            ?.let { false to it.errorMessage }
            ?: (true to null)
    }

}