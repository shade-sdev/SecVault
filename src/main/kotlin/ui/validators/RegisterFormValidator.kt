package ui.validators

import core.form.FormField
import core.form.FormFieldName
import core.form.validation.*


fun registerFormValidator(): FormValidator {
    return FormValidator()
            .addField(
                LoginFieldName.USERNAME, FormField(
                    validator = Validator()
                            .addRule(notNullRule(LoginFieldName.USERNAME.fieldName))
                            .addRule(lengthRule(LoginFieldName.USERNAME.fieldName, 1))
                )
            )
            .addField(
                LoginFieldName.EMAIL, FormField(
                    validator = Validator()
                            .addRule(notNullRule(LoginFieldName.EMAIL.fieldName))
                            .addRule(lengthRule(LoginFieldName.EMAIL.fieldName, 1))
                            .addRule(emailRule)
                )
            )
            .addField(
                LoginFieldName.PASSWORD, FormField(
                    validator = Validator()
                            .addRule(notNullRule(LoginFieldName.PASSWORD.fieldName))
                            .addRule(passwordRule)
                )
            )
            .validateAllFields()
}

enum class LoginFieldName(val fieldName: String) : FormFieldName {
    USERNAME("Username"),
    EMAIL("Email"),
    PASSWORD("Password");
}