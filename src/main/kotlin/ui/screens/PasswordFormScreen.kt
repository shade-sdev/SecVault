package ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import ui.components.forms.PasswordForm
import ui.validators.passwordFormValidator

class PasswordFormScreen : Screen {

    @Composable
    override fun Content() {

        val formValidator = remember { passwordFormValidator() }
        val navigator = LocalNavigator.current
        val isFormValid by formValidator.isValid

        PasswordForm(
            formValidator,
            isFormValid,
            onSaveClick = {},
            onCancelClick = { navigator?.pop() }
        )
    }

}