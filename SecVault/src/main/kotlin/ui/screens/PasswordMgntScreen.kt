package ui.screens

import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import com.dokar.sonner.ToastType
import com.dokar.sonner.Toaster
import com.dokar.sonner.rememberToasterState
import core.models.FormType
import core.models.UiState
import core.models.dto.PasswordDto
import kotlinx.coroutines.delay
import repository.password.Password
import ui.components.LoadingScreen
import ui.components.forms.passwordmgnt.PasswordForm
import ui.theme.tertiary
import ui.validators.passwordFormValidator
import viewmodel.PasswordMgntScreenModel
import kotlin.time.Duration.Companion.seconds

class PasswordMgntScreen(password: Password?, formType: FormType) : Screen {

    private val _password = password
    private val _formType = formType

    @Composable
    override fun Content() {

        val screenModel = koinScreenModel<PasswordMgntScreenModel>()
        val formValidator = remember { passwordFormValidator(_password, screenModel::decryptPassword) }
        val passwordState by screenModel.passwordState.collectAsState()
        val navigator = LocalNavigator.current
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
            onSaveClick = { password: PasswordDto -> screenModel.savePassword(_password?.id?.value, password, _formType) },
            onCancelClick = { navigator?.pop() },
            _formType
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

            is UiState.Idle -> {
                //noop
            }
        }

    }

}