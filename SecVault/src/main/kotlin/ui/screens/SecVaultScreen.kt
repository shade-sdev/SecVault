package ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import com.dokar.sonner.ToastType
import com.dokar.sonner.Toaster
import com.dokar.sonner.rememberToasterState
import core.models.UiState
import ui.components.LoadingScreen
import ui.components.dialog.MasterPasswordDialog
import ui.components.secvault.SecVaultContentScreen
import ui.theme.tertiary
import viewmodel.SecVaultScreenModel
import kotlin.time.Duration.Companion.seconds

class SecVaultScreen : Screen {

    @Composable
    override fun Content() {

        val screenModel = koinScreenModel<SecVaultScreenModel>()
        val secVaultState by screenModel.secVaultState.collectAsState()
        val masterPasswordDialogState = remember { mutableStateOf(!screenModel.isMasterPasswordPresent()) }
        val toaster = rememberToasterState()

        LaunchedEffect(Unit) {
            screenModel.loadCredentials()
        }

        Toaster(
            state = toaster,
            alignment = Alignment.TopEnd,
            darkTheme = true,
            showCloseButton = true,
            richColors = true
        )

        SecVaultContentScreen(screenModel)

        when (val state = secVaultState) {
            is UiState.Loading -> LoadingScreen(backgroundColor = tertiary.copy(alpha = 0.8f))
            is UiState.Success -> {
                screenModel.clearError()
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

        when {
            masterPasswordDialogState.value -> {
                MasterPasswordDialog(
                    dialogState = masterPasswordDialogState,
                    viewModel = screenModel
                )
            }
        }
    }
}