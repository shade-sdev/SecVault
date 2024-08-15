package ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import com.dokar.sonner.ToastType
import com.dokar.sonner.Toaster
import com.dokar.sonner.rememberToasterState
import core.ui.UiState
import ui.components.LoadingScreen
import ui.components.LoginScreenContent
import ui.theme.tertiary
import viewmodel.LoginScreenModel
import kotlin.time.Duration.Companion.seconds

class LoginScreen : Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current
        val screenModel = koinScreenModel<LoginScreenModel>()
        val loginState by screenModel.loginState.collectAsState()
        val toaster = rememberToasterState()

        Toaster(
            state = toaster,
            alignment = Alignment.TopEnd,
            darkTheme = true,
            showCloseButton = true,
            richColors = true
        )

        LoginScreenContent(screenModel, navigator, loginState)

        when (val state = loginState) {
            is UiState.Loading -> LoadingScreen(backgroundColor = tertiary.copy(alpha = 0.8f))
            is UiState.Success -> {
                LaunchedEffect(state) {
                    navigator?.push(LoginSplashScreen())
                }
            }

            is UiState.Error -> {
                LaunchedEffect(toaster) {
                    toaster.show(
                        message = state.message,
                        type = ToastType.Error,
                        duration = 5.seconds
                    )
                    screenModel.clearError()
                }
            }

            is UiState.Idle -> {}
        }

    }
}