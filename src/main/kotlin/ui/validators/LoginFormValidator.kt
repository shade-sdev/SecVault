package ui.validators

import core.form.FormField
import core.form.FormFieldName
import core.form.validation.FormValidator
import core.form.validation.Validator
import core.form.validation.notNullRule

fun loginFormValidator(): FormValidator {
    return FormValidator()
            .addField(
                LoginFormFieldName.USERNAME, FormField(
                    validator = Validator()
                            .addRule(notNullRule(LoginFormFieldName.USERNAME.fieldName))
                )
            )
            .addField(
                LoginFormFieldName.PASSWORD, FormField(
                    validator = Validator()
                            .addRule(notNullRule(LoginFormFieldName.PASSWORD.fieldName))
                )
            )
            .addField(
                LoginFormFieldName.MASTER_PASSWORD, FormField(
                    validator = Validator()
                            .addRule(notNullRule(LoginFormFieldName.MASTER_PASSWORD.fieldName))
                )
            ).validateAllFields()
}

enum class LoginFormFieldName(val fieldName: String) : FormFieldName {
    USERNAME("Username"),
    PASSWORD("Password"),
    MASTER_PASSWORD("Master Password"),
}