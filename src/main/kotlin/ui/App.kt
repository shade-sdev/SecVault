package ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import core.AppState
import di.rememberLogger
import org.koin.java.KoinJavaComponent.getKoin
import ui.screens.LoginScreen
import ui.screens.RegisterScreen
import ui.theme.PasswordColors
import java.lang.invoke.MethodHandles

@Composable
fun App() {
    val appState = getKoin().get<AppState>()
    val log = rememberLogger(MethodHandles.lookup().lookupClass())
    val screen = if (appState.userExist()) LoginScreen() else RegisterScreen()

    MaterialTheme(colorScheme = PasswordColors) {
        Navigator(screen) { navigator: Navigator ->
            SlideTransition(navigator)
        }
    }

}