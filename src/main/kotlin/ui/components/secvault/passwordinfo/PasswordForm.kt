package ui.components.secvault.passwordinfo

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.models.DefaultMenuItem
import ui.components.HorizontalSpacer
import ui.theme.Font
import viewmodel.SecVaultScreenModel

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
fun PasswordForm(screenModel: SecVaultScreenModel) {
    Column(modifier = Modifier.fillMaxWidth().fillMaxHeight()) {

        Row(modifier = Modifier.weight(1f)) {
            PasswordFormTitle()
        }

        val menuItem = screenModel.selectedMenuItem.collectAsState()

        Row(modifier = Modifier.weight(9f)) {
            when (menuItem.value) {
                DefaultMenuItem.PASSWORDS -> {
                    PasswordCredentialForm(screenModel)
                }

                DefaultMenuItem.CREDIT_CARD -> {
                    CreditCardCredentialForm(screenModel)
                }

                DefaultMenuItem.NOTES -> {
                    NotesCredentialForm()
                }
            }
        }

    }
}