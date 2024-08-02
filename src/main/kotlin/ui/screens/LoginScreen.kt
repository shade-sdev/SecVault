package ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import ui.components.CloseButton
import ui.components.FormTextField
import ui.components.LoginLeftContent
import ui.components.PasswordTextField
import ui.theme.Font
import ui.theme.secondary
import ui.theme.tertiary

class LoginScreen : Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current

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
                        label = "Username",
                        icon = Icons.Filled.AccountCircle,
                        modifier = Modifier.height(40.dp).width(360.dp)
                    )
                    PasswordTextField(
                        label = "Password",
                        modifier = Modifier.height(40.dp).width(360.dp)
                    )
                    PasswordTextField(
                        label = "Master Password",
                        modifier = Modifier.height(40.dp).width(360.dp)
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        Button(
                            onClick = { navigator?.push(LoginSplashScreen()) },
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
                            onClick = { navigator?.pop() },
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
}