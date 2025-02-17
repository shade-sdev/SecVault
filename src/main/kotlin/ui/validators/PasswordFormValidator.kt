package ui.validators

import androidx.compose.runtime.mutableStateOf
import core.form.FormField
import core.form.FormFieldName
import core.form.validation.*
import core.models.dto.PasswordDto
import repository.password.Password
import repository.user.User

fun passwordFormValidator(password: Password?, decrypt: (String) -> String): FormValidator {
    val validator = FormValidator()
            .addField(
                PasswordFormFieldName.USERNAME, FormField(
                    validator = Validator(),
                    value = mutableStateOf(password?.username ?: "")
                )
            )
            .addField(
                PasswordFormFieldName.EMAIL, FormField(
                    validator = Validator()
                            .addRule(emailIfNotNullRule),
                    value = mutableStateOf(password?.email ?: "")
                )
            )
            .addField(
                PasswordFormFieldName.PASSWORD, FormField(
                    validator = Validator()
                            .addRule(notNullRule(PasswordFormFieldName.PASSWORD.fieldName)),
                    value = mutableStateOf(password?.password?.let { decrypt(it) } ?: "")
                )
            )
            .addField(
                PasswordFormFieldName.NAME, FormField(
                    validator = Validator()
                            .addRule(notNullRule(PasswordFormFieldName.NAME.fieldName))
                            .addRule(lengthRule(PasswordFormFieldName.NAME.fieldName, 1)),
                    value = mutableStateOf(password?.name ?: "")
                )
            )
            .addField(
                PasswordFormFieldName.WEBSITE_URL, FormField(
                    validator = Validator()
                            .addRule(notNullRule(PasswordFormFieldName.WEBSITE_URL.fieldName))
                            .addRule(lengthRule(PasswordFormFieldName.WEBSITE_URL.fieldName, 1))
                            .addRule(urlRule),
                    value = mutableStateOf(password?.website ?: "")
                )
            )
            .addField(
                PasswordFormFieldName.WEBSITE_ICON_URL, FormField(
                    validator = Validator()
                            .addRule(urlRule),
                    value = mutableStateOf(password?.websiteIcon ?: "")
                )
            ).addField(
                PasswordFormFieldName.PASSWORD_CATEGORY, FormField(
                    validator = Validator(),
                    value = mutableStateOf(password?.passwordCategory ?: "")
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

    return validator
}

fun toPasswordDto(formValidator: FormValidator, user: User): PasswordDto {
    return PasswordDto(
        formValidator.getField(PasswordFormFieldName.USERNAME)?.value?.value,
        formValidator.getField(PasswordFormFieldName.EMAIL)?.value?.value,
        formValidator.getField(PasswordFormFieldName.PASSWORD)?.value?.value!!,
        formValidator.getField(PasswordFormFieldName.NAME)?.value?.value!!,
        formValidator.getField(PasswordFormFieldName.WEBSITE_URL)?.value?.value,
        formValidator.getField(PasswordFormFieldName.WEBSITE_ICON_URL)?.value?.value,
        formValidator.getField(PasswordFormFieldName.PASSWORD_CATEGORY)?.value?.value?.takeIf { it.isNotEmpty() },
        user
    )
}

enum class PasswordFormFieldName(val fieldName: String) : FormFieldName {
    USERNAME("Username"),
    EMAIL("Email"),
    PASSWORD("Password"),
    NAME("Name"),
    WEBSITE_URL("Website URL"),
    WEBSITE_ICON_URL("Website Icon URL"),
    PASSWORD_CATEGORY("Password Category"),
}