package ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import com.dokar.sonner.ToastType
import com.dokar.sonner.Toaster
import com.dokar.sonner.rememberToasterState
import core.models.UiState
import ui.components.setting.SettingScreenContent
import viewmodel.SettingScreenModel
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class SettingScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<SettingScreenModel>()
        val settingState by screenModel.settingState.collectAsState()
        val toasterState = rememberToasterState()

        val fileDialogState by screenModel.fileDialogState.collectAsState()

        Toaster(
            state = toasterState,
            alignment = Alignment.TopEnd,
            darkTheme = true,
            showCloseButton = true,
            richColors = true
        )

        when {
            fileDialogState -> {
                screenModel.showSelectFileDialog()?.readBytes()?.let {
                    screenModel.saveConfigFile(it)
                    screenModel.closeDialog()
                }
            }
        }

        SettingScreenContent(screenModel)

        when (val state = settingState) {
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

            UiState.Idle -> {}
            UiState.Loading -> {}
            is UiState.Success<*> -> {
                LaunchedEffect(toasterState) {
                    toasterState.show(
                        message = state.message!!,
                        type = ToastType.Success,
                        duration = 5.seconds
                    )
                    screenModel.clearError()
                }
            }
        }

    }

}