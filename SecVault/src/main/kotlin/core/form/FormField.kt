package core.form

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import core.form.validation.Validator

/**
 * Represents a form field with a value, an error state, and a validator.
 */
data class FormField(
    var value: MutableState<String> = mutableStateOf(""),
    var error: MutableState<String?> = mutableStateOf(null),
    val validator: Validator
)