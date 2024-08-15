package ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.Navigator
import core.models.UiState
import repository.user.User
import ui.screens.ForgotPasswordScreen
import ui.screens.LoginScreen
import ui.screens.RegisterScreen
import ui.theme.Font
import ui.theme.secondary
import ui.theme.tertiary
import ui.validators.ForgotPasswordFieldName
import ui.validators.RegisterFieldName
import ui.validators.forgotPasswordFormValidator
import ui.validators.registerFormValidator
import viewmodel.ForgotPasswordScreenModel
import viewmodel.LoginScreenModel
import viewmodel.RegisterScreenModel

@Composable
fun LoginScreenContent(
    screenModel: LoginScreenModel,
    navigator: Navigator?,
    loginState: UiState<User>
) {

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Row(
        modifier = Modifier.fillMaxSize()
                .background(color = tertiary)
    )
    {
        Box(
            modifier = Modifier.fillMaxSize().weight(1f)
        )
        {
            LoginLeftContent()
        }

        Column(
            modifier = Modifier.fillMaxSize()
                    .fillMaxHeight()
                    .weight(1f)
        )
        {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.End
            ) {
                CloseButton()
            }
            Column(
                modifier = Modifier.fillMaxSize()
                        .fillMaxHeight()
                        .weight(1f)
                        .background(color = tertiary),
                verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Login into your account.",
                    color = Color.White,
                    fontFamily = Font.RussoOne,
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(10.dp))
                FormTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = "Username",
                    icon = Icons.Filled.AccountCircle,
                    modifier = Modifier.height(40.dp).width(360.dp)
                )
                PasswordTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Password",
                    modifier = Modifier.height(40.dp).width(360.dp)
                )
                PasswordTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Master Password",
                    modifier = Modifier.height(40.dp).width(360.dp)
                )
                Row(
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.width(360.dp).height(25.dp)
                ) {
                    Text(
                        text = "Forgot Password?",
                        color = Color.White,
                        fontFamily = Font.RobotoRegular,
                        fontWeight = FontWeight.Normal,
                        fontSize = 11.sp,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null,
                                    onClick = { navigator?.push(ForgotPasswordScreen()) }
                                )
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(
                        enabled = loginState !is UiState.Loading,
                        onClick = { screenModel.login(username, password) },
                        modifier = Modifier.width(175.dp),
                        colors = ButtonColors(
                            containerColor = secondary,
                            contentColor = Color.White,
                            disabledContentColor = secondary,
                            disabledContainerColor = secondary
                        )
                    )
                    {
                        Text(
                            text = "Login",
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            fontFamily = Font.RussoOne
                        )
                    }
                    Button(
                        enabled = loginState !is UiState.Loading,
                        onClick = {
                            if (navigator?.canPop == true) navigator.pop() else navigator?.push(RegisterScreen())
                        },
                        modifier = Modifier.width(175.dp),
                        colors = ButtonColors(
                            containerColor = secondary,
                            contentColor = Color.White,
                            disabledContentColor = secondary,
                            disabledContainerColor = secondary
                        )
                    )
                    {
                        Text(
                            text = "Register",
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            fontFamily = Font.RussoOne
                        )
                    }
                }
            }
        }
    }
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

    Row(
        modifier = Modifier.fillMaxSize()
                .background(color = tertiary)
    )
    {
        Box(
            modifier = Modifier.fillMaxSize().weight(1f)
        )
        {
            LoginLeftContent()
        }

        Column(
            modifier = Modifier.fillMaxSize()
                    .weight(1f)
        )
        {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.End
            ) {
                CloseButton()
            }
            Column(
                modifier = Modifier.fillMaxSize()
                        .weight(1f)
                        .background(color = tertiary),
                verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Register an account.",
                    color = Color.White,
                    fontFamily = Font.RussoOne,
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.Start
                ) {
                    FormTextField(
                        value = username?.value?.value ?: "",
                        onValueChange = { newValue ->
                            username?.value?.value = newValue
                            formValidator.validateField(RegisterFieldName.USERNAME)
                        },
                        label = "Username",
                        icon = Icons.Filled.AccountCircle,
                        modifier = Modifier.height(40.dp).width(360.dp)
                    )
                    username?.error?.value?.let {
                        Text(
                            text = it,
                            color = Color.Red,
                            fontFamily = Font.RobotoRegular,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.Start
                ) {
                    FormTextField(
                        value = email?.value?.value ?: "",
                        onValueChange = { newValue ->
                            email?.value?.value = newValue
                            formValidator.validateField(RegisterFieldName.EMAIL)
                        },
                        label = "Email",
                        icon = Icons.Filled.Email,
                        modifier = Modifier.height(40.dp).width(360.dp)
                    )
                    email?.error?.value?.let {
                        Text(
                            text = it,
                            color = Color.Red,
                            fontFamily = Font.RobotoRegular,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.Start
                ) {
                    PasswordTextField(
                        value = password?.value?.value ?: "",
                        onValueChange = { newValue ->
                            password?.value?.value = newValue
                            formValidator.validateField(RegisterFieldName.PASSWORD)
                        },
                        label = "Password",
                        modifier = Modifier.height(40.dp).width(360.dp)
                    )
                    password?.error?.value?.let {
                        Text(
                            text = it,
                            color = Color.Red,
                            fontFamily = Font.RobotoRegular,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(
                        onClick = {
                            registerScreenModel.register(
                                username!!.value.value,
                                email!!.value.value,
                                password!!.value.value
                            )
                        },
                        modifier = Modifier.width(175.dp),
                        enabled = isFormValid,
                        colors = ButtonColors(
                            containerColor = secondary,
                            contentColor = Color.White,
                            disabledContentColor = secondary,
                            disabledContainerColor = secondary,
                        )
                    )
                    {
                        Text(
                            color = Color.White,
                            text = "Register",
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            fontFamily = Font.RussoOne
                        )
                    }
                    Button(
                        onClick = {
                            if (navigator?.canPop == true) navigator.pop() else navigator?.push(LoginScreen())
                        },
                        modifier = Modifier.width(175.dp),
                        colors = ButtonColors(
                            containerColor = secondary,
                            contentColor = Color.White,
                            disabledContentColor = secondary,
                            disabledContainerColor = secondary
                        )
                    )
                    {
                        Text(
                            text = "Login",
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            fontFamily = Font.RussoOne
                        )
                    }
                }
            }
        }

    }
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

    Row(
        modifier = Modifier.fillMaxSize()
                .background(color = tertiary)
    )
    {
        Box(
            modifier = Modifier.fillMaxSize().weight(1f)
        )
        {
            LoginLeftContent()
        }

        Column(
            modifier = Modifier.fillMaxSize()
                    .weight(1f)
        )
        {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.End
            ) {
                CloseButton()
            }
            Column(
                modifier = Modifier.fillMaxSize()
                        .weight(1f)
                        .background(color = tertiary),
                verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Reset your password.",
                    color = Color.White,
                    fontFamily = Font.RussoOne,
                    fontWeight = FontWeight.Normal,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.Start
                ) {
                    FormTextField(
                        value = email?.value?.value ?: "",
                        onValueChange = { newValue ->
                            email?.value?.value = newValue
                            formValidator.validateField(ForgotPasswordFieldName.EMAIL)
                        },
                        label = "Email",
                        icon = Icons.Filled.Email,
                        modifier = Modifier.height(40.dp).width(360.dp)
                    )
                    email?.error?.value?.let {
                        Text(
                            text = it,
                            color = Color.Red,
                            fontFamily = Font.RobotoRegular,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.Start
                ) {
                    PasswordTextField(
                        value = newPassword?.value?.value ?: "",
                        onValueChange = { newValue ->
                            newPassword?.value?.value = newValue
                            formValidator.validateField(ForgotPasswordFieldName.NEW_PASSWORD)
                        },
                        label = "New Password",
                        modifier = Modifier.height(40.dp).width(360.dp)
                    )
                    newPassword?.error?.value?.let {
                        Text(
                            text = it,
                            color = Color.Red,
                            fontFamily = Font.RobotoRegular,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.Start
                ) {
                    FormTextField(
                        value = otp?.value?.value ?: "",
                        onValueChange = { newValue ->
                            otp?.value?.value = newValue
                            formValidator.validateField(ForgotPasswordFieldName.ONE_TIME_PASSWORD)
                        },
                        label = "One Time Password",
                        icon = Icons.Filled.Security,
                        modifier = Modifier.height(40.dp).width(360.dp)
                    )
                    otp?.error?.value?.let {
                        Text(
                            text = it,
                            color = Color.Red,
                            fontFamily = Font.RobotoRegular,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(
                        onClick = {
                            forgotPasswordScreenModel.resetPassword(
                                email!!.value.value,
                                newPassword!!.value.value,
                                otp!!.value.value,
                            )
                        },
                        modifier = Modifier.width(175.dp),
                        enabled = isFormValid,
                        colors = ButtonColors(
                            containerColor = secondary,
                            contentColor = Color.White,
                            disabledContentColor = secondary,
                            disabledContainerColor = secondary,
                        )
                    )
                    {
                        Text(
                            color = Color.White,
                            text = "Reset",
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            fontFamily = Font.RussoOne
                        )
                    }
                    Button(
                        onClick = {
                            if (navigator?.canPop == true) navigator.pop() else navigator?.push(LoginScreen())
                        },
                        modifier = Modifier.width(175.dp),
                        colors = ButtonColors(
                            containerColor = secondary,
                            contentColor = Color.White,
                            disabledContentColor = secondary,
                            disabledContainerColor = secondary
                        )
                    )
                    {
                        Text(
                            text = "Login",
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            fontFamily = Font.RussoOne
                        )
                    }
                }
            }
        }

    }
}