package ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import di.rememberLogger
import ui.screens.RegisterScreen
import ui.theme.PasswordColors
import java.lang.invoke.MethodHandles

@Composable
fun App() {

    val log = rememberLogger(MethodHandles.lookup().lookupClass())

    MaterialTheme(colorScheme = PasswordColors) {
        Navigator(RegisterScreen()) { navigator: Navigator ->
            SlideTransition(navigator)
        }
    }

}