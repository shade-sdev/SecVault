package core.form.validation

import core.form.FormField
import core.form.FormFieldName

class FormValidator {

    private val fields = mutableMapOf<FormFieldName, FormField>()

    fun addField(name: FormFieldName, field: FormField): FormValidator {
        fields[name] = field
        return this
    }

    fun validateField(name: FormFieldName) {
        fields[name]?.let { field ->
            val (isValid, errorMessage) = field.validator.validate(field.value.value)
            field.error.value = if (isValid) null else errorMessage
        }
    }

    fun getField(name: FormFieldName): FormField? = fields[name]

    fun validateAll(): Boolean {
        return fields.values.all { field ->
            validateField(field)
            field.error.value == null
        }
    }

    private fun validateField(field: FormField) {
        val (isValid, errorMessage) = field.validator.validate(field.value.value)
        field.error.value = if (isValid) null else errorMessage
    }

}