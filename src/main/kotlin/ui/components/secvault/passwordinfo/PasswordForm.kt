package ui.components.secvault.passwordinfo

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.components.HorizontalSpacer
import ui.components.UnderLineTextFiled
import ui.theme.Font

@Composable
fun PasswordFormTitle() {
    Column() {
        Row() {
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Details",
                color = Color(0xFFFF6363),
                fontFamily = Font.RussoOne,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
            )
        }
        Row() {
            Column(modifier = Modifier.weight(0.3f)) {
                HorizontalSpacer(thickness = 1.dp, width = 1f, color = Color(0xFFFF6363))
            }

            Column(modifier = Modifier.weight(0.7f)) {
                HorizontalSpacer(thickness = 1.dp, width = 1f, color = Color(0xFF333333))
            }
        }
    }
}

@Composable
fun PasswordForm() {
    Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {

        Row(modifier = Modifier.weight(1f)) {
            PasswordFormTitle()
        }

        Row(modifier = Modifier.weight(9f)) {
            var userName by remember { mutableStateOf("") }

            Column() {
                Row(modifier = Modifier.weight(1f)) {
                    UnderLineTextFiled(
                        field = userName,
                        onFieldChange = { userName = it },
                        label = "Username",
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Row(modifier = Modifier.weight(1f)) {
                    UnderLineTextFiled(
                        field = userName,
                        onFieldChange = { userName = it },
                        label = "Password",
                        modifier = Modifier.fillMaxWidth(),
                        isPassword = true
                    )
                }

                Row(modifier = Modifier.weight(1f)) {
                    UnderLineTextFiled(
                        field = userName,
                        onFieldChange = { userName = it },
                        label = "Email",
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Row(modifier = Modifier.weight(1f)) {
                    UnderLineTextFiled(
                        field = userName,
                        onFieldChange = { userName = it },
                        label = "Website",
                        modifier = Modifier.fillMaxWidth()
                    )
                }

            }
        }

    }
}