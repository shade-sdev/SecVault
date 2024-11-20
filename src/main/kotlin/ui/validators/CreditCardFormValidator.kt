package ui.validators

import core.form.FormField
import core.form.FormFieldName
import core.form.validation.*
import core.models.dto.CreditCardDto
import core.models.dto.PasswordDto
import repository.user.User

fun creditCardFormValidator(): FormValidator {
    return FormValidator()
        .addField(
            CreditCardFormFieldName.CARD_NAME, FormField(
                validator = Validator().addRule(notNullRule(CreditCardFormFieldName.CARD_NAME.fieldName))
            )
        )
        .addField(
            CreditCardFormFieldName.CARD_OWNER, FormField(
                validator = Validator().addRule(notNullRule(CreditCardFormFieldName.CARD_OWNER.fieldName))
            )
        )
        .addField(
            CreditCardFormFieldName.CARD_NUMBER, FormField(
                validator = Validator().addRule(creditCardRule)
                    .addRule(notNullRule(CreditCardFormFieldName.CARD_NUMBER.fieldName))
            )
        )
        .addField(
            CreditCardFormFieldName.CARD_NUMBER, FormField(
                validator = Validator().addRule(creditCardRule)
                    .addRule(notNullRule(CreditCardFormFieldName.CARD_NUMBER.fieldName))
            )
        )
        .addField(
            CreditCardFormFieldName.CARD_PIN, FormField(
                validator = Validator().addRule(creditCardPinRule)
                    .addRule(notNullRule(CreditCardFormFieldName.CARD_PIN.fieldName))
            )
        )
        .addField(
            CreditCardFormFieldName.CARD_CVC, FormField(
                validator = Validator().addRule(creditCardPinRule)
                    .addRule(notNullRule(CreditCardFormFieldName.CARD_CVC.fieldName))
            )
        )
        .addField(
            CreditCardFormFieldName.CARD_EXPIRY, FormField(
                validator = Validator().addRule(notNullRule(CreditCardFormFieldName.CARD_EXPIRY.fieldName))
                    .addRule(creditCardExpiryDateRule)
            )
        )
        .addField(
            CreditCardFormFieldName.CARD_NOTES, FormField(
                validator = Validator()
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