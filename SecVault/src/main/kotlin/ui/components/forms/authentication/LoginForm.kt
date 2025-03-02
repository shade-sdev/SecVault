package ui.components.forms.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.form.FormField
import core.form.validation.FormValidator
import core.models.UiState
import repository.user.User
import ui.components.CloseButton
import ui.components.FormTextField
import ui.components.LoginLeftContent
import ui.components.PasswordTextField
import ui.theme.Font
import ui.theme.secondary
import ui.theme.tertiary
import ui.validators.LoginFormFieldName

@Composable
fun LoginForm(
    username: FormField?,
    password: FormField?,
    masterPassword: FormField?,
    formValidator: FormValidator,
    loginState: UiState<User>,
    onLoginClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onRegisterClick: () -> Unit
) {
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
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.Start
                ) {
                    FormTextField(
                        value = username?.value?.value ?: "",
                        onValueChange = { newValue ->
                            username?.value?.value = newValue
                            formValidator.validateField(LoginFormFieldName.USERNAME)
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
                    PasswordTextField(
                        value = password?.value?.value ?: "",
                        onValueChange = { newValue ->
                            password?.value?.value = newValue
                            formValidator.validateField(LoginFormFieldName.PASSWORD)
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
                Column(
                    verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterVertically),
                    horizontalAlignment = Alignment.Start
                ) {
                    PasswordTextField(
                        value = masterPassword?.value?.value ?: "",
                        onValueChange = { newValue ->
                            masterPassword?.value?.value = newValue
                            formValidator.validateField(LoginFormFieldName.MASTER_PASSWORD)
                        },
                        label = "Master Password",
                        modifier = Modifier.height(40.dp).width(360.dp)
                    )
                    masterPassword?.error?.value?.let {
                        Text(
                            text = it,
                            color = Color.Red,
                            fontFamily = Font.RobotoRegular,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
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
                                    onClick = onForgotPasswordClick
                                )
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(
                        enabled = loginState !is UiState.Loading,
                        onClick = onLoginClick,
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
                            color = Color.White,
                            text = "Login",
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            fontFamily = Font.RussoOne
                        )
                    }
                    Button(
                        enabled = loginState !is UiState.Loading,
                        onClick = onRegisterClick,
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