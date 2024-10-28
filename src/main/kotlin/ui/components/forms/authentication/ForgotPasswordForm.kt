package ui.components.forms.authentication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import ui.components.CloseButton
import ui.components.FormTextField
import ui.components.LoginLeftContent
import ui.components.PasswordTextField
import ui.theme.Font
import ui.theme.secondary
import ui.theme.tertiary
import ui.validators.ForgotPasswordFieldName

@Composable
fun ForgotPasswordForm(
    email: FormField?,
    newPassword: FormField?,
    otp: FormField?,
    formValidator: FormValidator,
    isFormValid: Boolean,
    onResetClick: () -> Unit,
    onLoginClick: () -> Unit
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
                        onClick = onResetClick,
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