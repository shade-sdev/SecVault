package ui.screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import com.dokar.sonner.Toaster
import com.dokar.sonner.rememberToasterState
import core.ui.NotificationType
import core.ui.UiState
import ui.components.LoadingScreen
import ui.components.NotificationFactory
import ui.components.RegisterScreenContent
import ui.theme.tertiary
import viewmodel.RegisterScreenModel

class RegisterScreen : Screen {

    @Preview
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current
        val screenModel = koinScreenModel<RegisterScreenModel>()
        val registerState by screenModel.registerState.collectAsState()
        val toaster = rememberToasterState()

        Toaster(state = toaster, alignment = Alignment.TopEnd, darkTheme = true, showCloseButton = true)

        Box(modifier = Modifier.fillMaxSize()) {

            RegisterScreenContent(screenModel, navigator)

            when (val state = registerState) {
                is UiState.Loading -> LoadingScreen(backgroundColor = tertiary.copy(alpha = 0.8f))
                is UiState.Success -> {
                    screenModel.openQRCode(state.data)

                    NotificationFactory(
                        message = "Successfully registered",
                        visible = true,
                        onDismiss = { screenModel.clearError() },
                        type = NotificationType.SUCCESS
                    )
                }

                is UiState.Error -> {
                    NotificationFactory(
                        message = state.message,
                        visible = true,
                        onDismiss = { screenModel.clearError() },
                        type = NotificationType.ERROR
                    )
                }

                is UiState.Idle -> {}
            }
        }
    }

}