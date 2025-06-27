package ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import core.AppState
import org.koin.java.KoinJavaComponent.getKoin
import ui.theme.PasswordColors

/**
 * Main composable function for the application.
 * It sets up the application state, logger, and the theme.
 */
@Composable
fun App() {
    val appState = getKoin().get<AppState>()

    MaterialTheme(colorScheme = PasswordColors) {
        Navigator(appState.initialScreen()) { navigator: Navigator ->
            SlideTransition(navigator)
        }
    }

}