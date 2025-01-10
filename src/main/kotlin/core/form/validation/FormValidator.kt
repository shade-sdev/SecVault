package core.form.validation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import core.form.FormField
import core.form.FormFieldName

/**
 * A class responsible for managing and validating form fields.
 */
class FormValidator {

    private val fields = mutableMapOf<FormFieldName, FormField>()
    private val _isValid = mutableStateOf(true)

    /**
     * A read-only state representing the overall validity of the form.
     */
    val isValid: State<Boolean> get() = _isValid

    /**
     * Adds a form field to the validator.
     *
     * @param name The name of the form field.
     * @param field The form field to be added.
     * @return The current instance of FormValidator.
     */
    fun addField(name: FormFieldName, field: FormField): FormValidator {
        fields[name] = field
        return this
    }

    /**
     * Validates a specific form field by its name.
     *
     * @param name The name of the form field to validate.
     */
    fun validateField(name: FormFieldName) {
        fields[name]?.let { field ->
            val (isValid, errorMessage) = field.validator.validate(field.value.value)
            field.error.value = if (isValid) null else errorMessage
            updateFormValidity()
        }
    }

    /**
     * Retrieves a form field by its name.
     *
     * @param name The name of the form field.
     * @return The form field if found, otherwise null.
     */
    fun getField(name: FormFieldName): FormField? = fields[name]

    /**
     * Checks if all form fields are valid.
     *
     * @return True if all form fields are valid, otherwise false.
     */
    fun isValid(): Boolean {
        return fields.values.all { field ->
            validateField(field)
            field.error.value == null
        }
    }

    /**
     * Validates all form fields.
     *
     * @return The current instance of FormValidator.
     */
    fun validateAllFields(): FormValidator {
        fields.keys.forEach { name -> validateField(name) }
        return this
    }

    /**
     * Updates the overall validity state of the form.
     */
    private fun updateFormValidity() {
        _isValid.value = fields.values.all { field ->
            field.error.value == null
        }
    }

    /**
     * Validates a form field.
     *
     * @param field The form field to validate.
     */
    private fun validateField(field: FormField) {
        val (isValid, errorMessage) = field.validator.validate(field.value.value)
        field.error.value = if (isValid) null else errorMessage
    }

}