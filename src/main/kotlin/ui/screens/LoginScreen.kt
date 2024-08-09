package ui.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import core.ui.NotificationType
import core.ui.UiState
import ui.components.LoadingScreen
import ui.components.LoginScreenContent
import ui.components.NotificationFactory
import ui.theme.tertiary
import viewmodel.LoginScreenModel

class LoginScreen : Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current

        val screenModel = koinScreenModel<LoginScreenModel>()

        val loginState by screenModel.loginState.collectAsState()

        Box(modifier = Modifier.fillMaxSize()) {

            LoginScreenContent(screenModel, navigator, loginState)

            when (val state = loginState) {
                is UiState.Loading -> LoadingScreen(backgroundColor = tertiary.copy(alpha = 0.8f))
                is UiState.Success -> {
                    LaunchedEffect(state) {
                        navigator?.push(LoginSplashScreen())
                    }
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