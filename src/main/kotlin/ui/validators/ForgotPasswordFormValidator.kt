package ui.validators

import core.form.FormField
import core.form.FormFieldName
import core.form.validation.*

fun forgotPasswordFormValidator(): FormValidator {
    return FormValidator()
            .addField(
                ForgotPasswordFieldName.EMAIL, FormField(
                    validator = Validator()
                            .addRule(notNullRule(ForgotPasswordFieldName.EMAIL.fieldName))
                            .addRule(lengthRule(ForgotPasswordFieldName.EMAIL.fieldName, 1))
                            .addRule(emailRule)
                )
            )
            .addField(
                ForgotPasswordFieldName.NEW_PASSWORD, FormField(
                    validator = Validator()
                            .addRule(notNullRule(ForgotPasswordFieldName.NEW_PASSWORD.fieldName))
                            .addRule(passwordRule)
                )
            )
            .addField(
                ForgotPasswordFieldName.ONE_TIME_PASSWORD, FormField(
                    validator = Validator()
                            .addRule(notNullRule(ForgotPasswordFieldName.ONE_TIME_PASSWORD.fieldName))
                            .addRule(minMaxRule(ForgotPasswordFieldName.ONE_TIME_PASSWORD.fieldName, 6, 6))
                            .addRule(
                                instanceRule(
                                    ForgotPasswordFieldName.ONE_TIME_PASSWORD.fieldName,
                                    Integer::class.java
                                )
                            )
                )
            ).validateAllFields()
}

enum class ForgotPasswordFieldName(val fieldName: String) : FormFieldName {
    EMAIL("Email"),
    NEW_PASSWORD("New Password"),
    ONE_TIME_PASSWORD("One Time Password")
}