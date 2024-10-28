package ui.validators

import core.form.FormField
import core.form.FormFieldName
import core.form.validation.*

fun creditCardFormValidator(): FormValidator {
    return FormValidator()
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

enum class CreditCardFormFieldName(val fieldName: String) : FormFieldName {
    CARD_NUMBER("Card number"),
    CARD_PIN("Pin"),
    CARD_CVC("Cvc"),
    CARD_EXPIRY("Expiry Date"),
    CARD_NOTES("Notes")
}