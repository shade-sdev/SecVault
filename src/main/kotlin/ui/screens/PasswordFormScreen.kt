package ui.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import ui.components.forms.PasswordForm

class PasswordFormScreen : Screen {

    @Composable
    override fun Content() {
        PasswordForm()
    }

}