package ui.validators

import core.form.FormField
import core.form.FormFieldName
import core.form.validation.FormValidator
import core.form.validation.ValidationRule
import core.form.validation.Validator


fun registerFormValidator(): FormValidator {
    return FormValidator()
        .addField(
            LoginFieldName.USERNAME, FormField(
                validator = Validator()
                    .addRule(
                        ValidationRule(
                            condition = { it.length >= 8 },
                            errorMessage = "Username must be 8 characters or more"
                        )
                    )
            )
        )
}

enum class LoginFieldName : FormFieldName {
    USERNAME,
    EMAIL,
    PASSWORD
}