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
import core.models.UiState
import ui.components.ForgotPasswordScreenContent
import ui.components.LoadingScreen
import ui.theme.tertiary
import viewmodel.ForgotPasswordScreenModel
import kotlin.time.Duration.Companion.seconds

class ForgotPasswordScreen : Screen {

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        val screenModel = koinScreenModel<ForgotPasswordScreenModel>()
        val forgotPasswordState by screenModel.forgotPasswordState.collectAsState()
        val toaster = rememberToasterState()

        Toaster(
            state = toaster,
            alignment = Alignment.TopEnd,
            darkTheme = true,
            showCloseButton = true,
            richColors = true
        )

        ForgotPasswordScreenContent(screenModel, navigator)

        when (val state = forgotPasswordState) {
            is UiState.Loading -> LoadingScreen(backgroundColor = tertiary.copy(alpha = 0.8f))
            is UiState.Success -> {
                LaunchedEffect(toaster) {
                    toaster.show(
                        message = "Password has been Reset!",
                        type = ToastType.Success,
                        duration = 5.seconds
                    )
                    screenModel.clearError()
                    if (navigator?.canPop == true) navigator.pop() else navigator?.push(LoginScreen())
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