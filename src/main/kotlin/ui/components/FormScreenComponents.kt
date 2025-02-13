package ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import cafe.adriel.voyager.navigator.Navigator
import core.models.UiState
import repository.user.User
import ui.components.forms.authentication.ForgotPasswordForm
import ui.components.forms.authentication.LoginForm
import ui.components.forms.authentication.RegisterForm
import ui.screens.ForgotPasswordScreen
import ui.screens.LoginScreen
import ui.screens.RegisterScreen
import ui.validators.*
import viewmodel.ForgotPasswordScreenModel
import viewmodel.LoginScreenModel
import viewmodel.RegisterScreenModel

@Composable
fun LoginScreenContent(
    screenModel: LoginScreenModel,
    navigator: Navigator?,
    loginState: UiState<User>
) {

    val formValidator = remember { loginFormValidator() }

    val username = formValidator.getField(LoginFormFieldName.USERNAME)
    val password = formValidator.getField(LoginFormFieldName.PASSWORD)
    val masterPassword = formValidator.getField(LoginFormFieldName.MASTER_PASSWORD)

    val isFormValid by formValidator.isValid

    LoginForm(
        username = username,
        password = password,
        masterPassword = masterPassword,
        isFormValid = isFormValid,
        formValidator = formValidator,
        loginState = loginState,
        onLoginClick = {
            screenModel.login(
                username!!.value.value,
                password!!.value.value,
                masterPassword!!.value.value,
            )
        },
        onForgotPasswordClick = { navigator?.push(ForgotPasswordScreen()) },
        onRegisterClick = {
            if (navigator?.canPop == true) navigator.pop() else navigator?.push(RegisterScreen())
        }
    )
}

@Composable
fun RegisterScreenContent(
    registerScreenModel: RegisterScreenModel,
    navigator: Navigator?
) {
    val formValidator = remember { registerFormValidator() }

    val username = formValidator.getField(RegisterFieldName.USERNAME)
    val email = formValidator.getField(RegisterFieldName.EMAIL)
    val password = formValidator.getField(RegisterFieldName.PASSWORD)

    val isFormValid by formValidator.isValid

    RegisterForm(
        username = username,
        email = email,
        password = password,
        isFormValid = isFormValid,
        formValidator = formValidator,
        onRegisterClick = {
            registerScreenModel.register(
                username!!.value.value,
                email!!.value.value,
                password!!.value.value
            )
        },
        onLoginClick = { if (navigator?.canPop == true) navigator.pop() else navigator?.push(LoginScreen()) },
    )
}

@Composable
fun ForgotPasswordScreenContent(
    forgotPasswordScreenModel: ForgotPasswordScreenModel,
    navigator: Navigator?
) {
    val formValidator = remember {
        forgotPasswordFormValidator()
    }

    val email = formValidator.getField(ForgotPasswordFieldName.EMAIL)
    val newPassword = formValidator.getField(ForgotPasswordFieldName.NEW_PASSWORD)
    val otp = formValidator.getField(ForgotPasswordFieldName.ONE_TIME_PASSWORD)

    val isFormValid by formValidator.isValid

    ForgotPasswordForm(
        email = email,
        newPassword = newPassword,
        otp = otp,
        isFormValid = isFormValid,
        formValidator = formValidator,
        onLoginClick = { if (navigator?.canPop == true) navigator.pop() else navigator?.push(LoginScreen()) },
        onResetClick = {
            forgotPasswordScreenModel.resetPassword(
                email!!.value.value,
                newPassword!!.value.value,
                otp!!.value.value,
            )
        }
    )
}