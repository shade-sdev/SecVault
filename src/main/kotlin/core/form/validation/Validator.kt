package core.form.validation

class Validator {
    private val rules = mutableListOf<ValidationRule>()

    fun addRule(rule: ValidationRule): Validator {
        rules.add(rule)
        return this
    }

    fun validate(input: String): Pair<Boolean, String?> {
        val failedRule = rules.firstOrNull { !it.condition(input) }
        return if (failedRule != null) {
            Pair(false, failedRule.errorMessage)
        } else {
            Pair(true, null)
        }
    }

}