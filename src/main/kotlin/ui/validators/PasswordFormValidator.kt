package ui.validators

import core.form.FormField
import core.form.FormFieldName
import core.form.validation.*
import core.models.dto.PasswordDto
import repository.user.User

fun passwordFormValidator(): FormValidator {
    val validator = FormValidator()
        .addField(
            PasswordFormFieldName.USERNAME, FormField(
                validator = Validator()
            )
        )
        .addField(
            PasswordFormFieldName.EMAIL, FormField(
                validator = Validator()
                    .addRule(emailIfNotNullRule)
            )
        )
        .addField(
            PasswordFormFieldName.PASSWORD, FormField(
                validator = Validator()
                    .addRule(notNullRule(PasswordFormFieldName.PASSWORD.fieldName))
                    .addRule(passwordRule)
            )
        )
        .addField(
            PasswordFormFieldName.NAME, FormField(
                validator = Validator()
                    .addRule(notNullRule(PasswordFormFieldName.NAME.fieldName))
                    .addRule(lengthRule(PasswordFormFieldName.NAME.fieldName, 1))
            )
        )
        .addField(
            PasswordFormFieldName.WEBSITE_URL, FormField(
                validator = Validator()
                    .addRule(notNullRule(PasswordFormFieldName.WEBSITE_URL.fieldName))
                    .addRule(lengthRule(PasswordFormFieldName.WEBSITE_URL.fieldName, 1))
                    .addRule(urlRule)
            )
        )
        .addField(
            PasswordFormFieldName.WEBSITE_ICON_URL, FormField(
                validator = Validator()
                    .addRule(urlRule)
            )
        )

    validator.getField(PasswordFormFieldName.USERNAME)?.validator
        ?.addRule(
            conditionalRule(
                { validator.getField(PasswordFormFieldName.EMAIL)!! },
                listOf(notNullRule(PasswordFormFieldName.USERNAME.fieldName))
            )
        )

    validator.getField(PasswordFormFieldName.EMAIL)?.validator
        ?.addRule(
            conditionalRule(
                { validator.getField(PasswordFormFieldName.USERNAME)!! },
                listOf(notNullRule(PasswordFormFieldName.EMAIL.fieldName))
            )
        )

    return validator.validateAllFields()
}

fun toPasswordDto(formValidator: FormValidator, user: User): PasswordDto {
    return PasswordDto(
        formValidator.getField(PasswordFormFieldName.USERNAME)?.value?.value,
        formValidator.getField(PasswordFormFieldName.EMAIL)?.value?.value,
        formValidator.getField(PasswordFormFieldName.PASSWORD)?.value?.value!!,
        formValidator.getField(PasswordFormFieldName.NAME)?.value?.value!!,
        formValidator.getField(PasswordFormFieldName.WEBSITE_URL)?.value?.value,
        formValidator.getField(PasswordFormFieldName.WEBSITE_ICON_URL)?.value?.value,
        user
    )
}

enum class PasswordFormFieldName(val fieldName: String) : FormFieldName {
    USERNAME("Username"),
    EMAIL("Email"),
    PASSWORD("Password"),
    NAME("Name"),
    WEBSITE_URL("Website URL"),
    WEBSITE_ICON_URL("Website Icon URL");
}