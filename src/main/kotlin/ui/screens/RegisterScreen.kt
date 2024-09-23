package ui.screens

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.painter.BitmapPainter
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import com.dokar.sonner.ToastType
import com.dokar.sonner.Toaster
import com.dokar.sonner.rememberToasterState
import core.models.Result
import core.models.UiState
import ui.components.LoadingScreen
import ui.components.RegisterScreenContent
import ui.components.forms.QRCodeDialog
import ui.theme.tertiary
import viewmodel.RegisterScreenModel
import kotlin.time.Duration

class RegisterScreen : Screen {

    @Preview
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current
        val screenModel = koinScreenModel<RegisterScreenModel>()
        val registerState by screenModel.registerState.collectAsState()
        val toasterState = rememberToasterState()
        val qrCodeDialogState = remember { mutableStateOf(false) }
        val qrCodePainterState = remember { mutableStateOf<BitmapPainter?>(null) }

        Toaster(
            state = toasterState,
            alignment = Alignment.TopEnd,
            darkTheme = true,
            showCloseButton = true,
            richColors = true
        )

        RegisterScreenContent(screenModel, navigator)

        when (val state = registerState) {
            is UiState.Loading -> LoadingScreen(backgroundColor = tertiary.copy(alpha = 0.8f))
            is UiState.Success -> {
                LaunchedEffect(toasterState) {
                    toasterState.show(
                        message = "Successfully Registered",
                        type = ToastType.Success,
                        duration = Duration.INFINITE
                    )
                    screenModel.clearError()

                    when (val qRCodeResult: Result<BitmapPainter> = screenModel.openQRCode(state.data)) {
                        is Result.Success -> {
                            qrCodeDialogState.value = true
                            qrCodePainterState.value = qRCodeResult.data
                        }

                        is Result.Error -> {
                            toasterState.show(
                                message = "Could not generate QR Code",
                                type = ToastType.Error,
                                duration = Duration.INFINITE
                            )
                        }
                    }
                }
            }

            is UiState.Error -> {
                LaunchedEffect(toasterState) {
                    toasterState.show(
                        message = state.message,
                        type = ToastType.Error,
                        duration = Duration.INFINITE
                    )
                    screenModel.clearError()
                }
            }

            is UiState.Idle -> {}
        }

        when {
            qrCodeDialogState.value -> {
                QRCodeDialog(
                    qrCodeDialogState = qrCodeDialogState,
                    bitmapPainter = qrCodePainterState.value!!
                )
            }
        }

    }

}