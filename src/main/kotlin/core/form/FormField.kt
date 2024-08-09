package core.form

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import core.form.validation.Validator

data class FormField(
    var value: MutableState<String> = mutableStateOf(""),
    var error: MutableState<String?> = mutableStateOf(null),
    val validator: Validator
)