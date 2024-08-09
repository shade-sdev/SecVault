package core.form.validation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import core.form.FormField
import core.form.FormFieldName

class FormValidator {

    private val fields = mutableMapOf<FormFieldName, FormField>()
    private val _isValid = mutableStateOf(true)

    val isValid: State<Boolean> get() = _isValid


    fun addField(name: FormFieldName, field: FormField): FormValidator {
        fields[name] = field
        return this
    }

    fun validateField(name: FormFieldName) {
        fields[name]?.let { field ->
            val (isValid, errorMessage) = field.validator.validate(field.value.value)
            field.error.value = if (isValid) null else errorMessage
            updateFormValidity()
        }
    }

    fun getField(name: FormFieldName): FormField? = fields[name]

    fun isValid(): Boolean {
        return fields.values.all { field ->
            validateField(field)
            field.error.value == null
        }
    }

    fun validateAllFields(): FormValidator {
        fields.keys.forEach { name -> validateField(name) }
        return this
    }

    private fun updateFormValidity() {
        _isValid.value = fields.values.all { field ->
            field.error.value == null
        }
    }

    private fun validateField(field: FormField) {
        val (isValid, errorMessage) = field.validator.validate(field.value.value)
        field.error.value = if (isValid) null else errorMessage
    }

}