package ui.validators

import androidx.compose.runtime.mutableStateOf
import core.form.FormField
import core.form.FormFieldName
import core.form.validation.*
import core.models.dto.CreditCardDto
import repository.creditcard.CreditCard
import repository.user.User

fun creditCardFormValidator(creditCard: CreditCard?): FormValidator {
    return FormValidator()
            .addField(
                CreditCardFormFieldName.CARD_NAME, FormField(
                    validator = Validator().addRule(notNullRule(CreditCardFormFieldName.CARD_NAME.fieldName)),
                    value = mutableStateOf(creditCard?.name ?: "")
                )
            )
            .addField(
                CreditCardFormFieldName.CARD_OWNER, FormField(
                    validator = Validator().addRule(notNullRule(CreditCardFormFieldName.CARD_OWNER.fieldName)),
                    value = mutableStateOf(creditCard?.owner?.id?.value?.toString() ?: "")
                )
            )
            .addField(
                CreditCardFormFieldName.CARD_NUMBER, FormField(
                    validator = Validator().addRule(creditCardRule)
                            .addRule(notNullRule(CreditCardFormFieldName.CARD_NUMBER.fieldName)),
                    value = mutableStateOf(creditCard?.number ?: "")
                )
            )
            .addField(
                CreditCardFormFieldName.CARD_PIN, FormField(
                    validator = Validator().addRule(creditCardPinRule)
                            .addRule(notNullRule(CreditCardFormFieldName.CARD_PIN.fieldName)),
                    value = mutableStateOf(creditCard?.pin?.toString() ?: "")
                )
            )
            .addField(
                CreditCardFormFieldName.CARD_CVC, FormField(
                    validator = Validator().addRule(creditCardPinRule)
                            .addRule(notNullRule(CreditCardFormFieldName.CARD_CVC.fieldName)),
                    value = mutableStateOf(creditCard?.cvc?.toString() ?: "")
                )
            )
            .addField(
                CreditCardFormFieldName.CARD_EXPIRY, FormField(
                    validator = Validator().addRule(notNullRule(CreditCardFormFieldName.CARD_EXPIRY.fieldName))
                            .addRule(creditCardExpiryDateRule),
                    value = mutableStateOf(creditCard?.expiryDate ?: "")
                )
            )
            .addField(
                CreditCardFormFieldName.CARD_NOTES, FormField(
                    validator = Validator(),
                    value = mutableStateOf(creditCard?.notes ?: "")
                )
            )
            .validateAllFields()
}

fun toCreditCardDto(formValidator: FormValidator, user: User, owner: User): CreditCardDto {
    return CreditCardDto(
        user,
        formValidator.getField(CreditCardFormFieldName.CARD_NAME)?.value?.value!!,
        owner,
        formValidator.getField(CreditCardFormFieldName.CARD_NUMBER)?.value?.value!!,
        formValidator.getField(CreditCardFormFieldName.CARD_CVC)?.value?.value!!.toInt(),
        formValidator.getField(CreditCardFormFieldName.CARD_PIN)?.value?.value!!.toInt(),
        formValidator.getField(CreditCardFormFieldName.CARD_EXPIRY)?.value?.value!!,
        formValidator.getField(CreditCardFormFieldName.CARD_NOTES)?.value?.value,
    )
}

enum class CreditCardFormFieldName(val fieldName: String) : FormFieldName {
    CARD_NAME("Bank Name"),
    CARD_OWNER("Card Owner"),
    CARD_NUMBER("Card number"),
    CARD_PIN("Pin"),
    CARD_CVC("Cvc"),
    CARD_EXPIRY("Expiry Date"),
    CARD_NOTES("Notes")
}