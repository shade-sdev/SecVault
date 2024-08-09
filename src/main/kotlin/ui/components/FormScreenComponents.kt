package ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.MaterialTheme
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
import core.ui.UiState
import repository.user.User
import ui.screens.LoginScreen
import ui.screens.RegisterScreen
import ui.theme.Font
import ui.theme.secondary
import ui.theme.tertiary
import ui.validators.LoginFieldName
import ui.validators.registerFormValidator
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
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val formValidator = remember { registerFormValidator() }

    val usernameField = formValidator.getField(LoginFieldName.USERNAME)

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
                FormTextField(
                    value = usernameField?.value?.value ?: "",
                    onValueChange = { newValue ->
                        usernameField?.value?.value = newValue
                        formValidator.validateField(LoginFieldName.USERNAME)
                    },
                    label = "Username",
                    icon = Icons.Filled.AccountCircle,
                    modifier = Modifier.height(40.dp).width(360.dp)
                )
                usernameField?.error?.value?.let {
                    Text(
                        text = it,
                        color = Color.Red,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
                FormTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Email",
                    icon = Icons.Filled.Email,
                    modifier = Modifier.height(40.dp).width(360.dp)
                )
                PasswordTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Password",
                    modifier = Modifier.height(40.dp).width(360.dp)
                )
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(
                        onClick = { },
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