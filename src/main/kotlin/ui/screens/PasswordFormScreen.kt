package ui.screens

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import com.dokar.sonner.ToastType
import com.dokar.sonner.Toaster
import com.dokar.sonner.rememberToasterState
import core.models.UiState
import core.models.dto.PasswordDto
import kotlinx.coroutines.delay
import ui.components.LoadingScreen
import ui.components.forms.PasswordForm
import ui.theme.tertiary
import ui.validators.passwordFormValidator
import viewmodel.PasswordMgntScreenModel
import kotlin.time.Duration.Companion.seconds

class PasswordFormScreen : Screen {

    @Composable
    override fun Content() {

        val formValidator = remember { passwordFormValidator() }
        val screenModel = koinScreenModel<PasswordMgntScreenModel>()
        val passwordState by screenModel.passwordState.collectAsState()
        val navigator = LocalNavigator.current
        val isFormValid by formValidator.isValid
        val toaster = rememberToasterState()

        Toaster(
            state = toaster,
            alignment = Alignment.TopEnd,
            darkTheme = true,
            showCloseButton = true,
            richColors = true
        )

        PasswordForm(
            formValidator,
            screenModel,
            isFormValid,
            onSaveClick = { password: PasswordDto -> screenModel.savePassword(password) },
            onCancelClick = { navigator?.pop() }
        )

        when (val state = passwordState) {
            is UiState.Loading -> LoadingScreen(backgroundColor = tertiary.copy(alpha = 0.8f))
            is UiState.Success -> {
                LaunchedEffect(state) {
                    toaster.show(
                        message = "Password was successfully added",
                        type = ToastType.Success,
                        duration = 2.seconds
                    )
                    delay(500)
                    navigator?.pop()
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