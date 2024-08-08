package ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import core.AppState
import di.rememberLogger
import org.koin.java.KoinJavaComponent.getKoin
import ui.theme.PasswordColors
import java.lang.invoke.MethodHandles

@Composable
fun App() {
    val appState = getKoin().get<AppState>()
    val log = rememberLogger(MethodHandles.lookup().lookupClass())

    MaterialTheme(colorScheme = PasswordColors) {
        Navigator(appState.initialScreen()) { navigator: Navigator ->
            SlideTransition(navigator)
        }
    }

}