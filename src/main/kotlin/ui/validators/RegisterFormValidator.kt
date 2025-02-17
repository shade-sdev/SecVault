package ui.validators

import core.form.FormField
import core.form.FormFieldName
import core.form.validation.*


fun registerFormValidator(): FormValidator {
    return FormValidator()
            .addField(
                RegisterFieldName.USERNAME, FormField(
                    validator = Validator()
                            .addRule(notNullRule(RegisterFieldName.USERNAME.fieldName))
                            .addRule(lengthRule(RegisterFieldName.USERNAME.fieldName, 1))
                )
            )
            .addField(
                RegisterFieldName.EMAIL, FormField(
                    validator = Validator()
                            .addRule(notNullRule(RegisterFieldName.EMAIL.fieldName))
                            .addRule(lengthRule(RegisterFieldName.EMAIL.fieldName, 1))
                            .addRule(emailRule)
                )
            )
            .addField(
                RegisterFieldName.PASSWORD, FormField(
                    validator = Validator()
                            .addRule(notNullRule(RegisterFieldName.PASSWORD.fieldName))
                            .addRule(passwordRule)
                )
            )
}

enum class RegisterFieldName(val fieldName: String) : FormFieldName {
    USERNAME("Username"),
    EMAIL("Email"),
    PASSWORD("Password");
}