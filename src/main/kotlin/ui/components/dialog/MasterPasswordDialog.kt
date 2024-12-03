package ui.components.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
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
import ui.components.PasswordTextField
import ui.components.SecVaultDialog
import ui.theme.Font
import ui.theme.secondary
import ui.theme.tertiary
import viewmodel.SecVaultScreenModel

@Composable
fun MasterPasswordDialog(
    viewModel: SecVaultScreenModel,
    dialogState: MutableState<Boolean>
) {
    var masterPassword by remember { mutableStateOf("") }

    SecVaultDialog(
        onDismissRequest = { dialogState.value = false },
        modifier = Modifier.fillMaxWidth()
            .width(50.dp)
            .height(400.dp),
        roundedSize = 20.dp,
        backgroundColor = tertiary
    ) {

        Column(
            modifier = Modifier.fillMaxSize()
                .fillMaxHeight()
                .background(color = tertiary),
            verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = Icons.Filled.Warning,
                contentDescription = "Warning",
                modifier = Modifier.size(30.dp),
                tint = Color.Yellow
            )
            Text(
                text = "Enter your master password.",
                color = Color.White,
                fontFamily = Font.RussoOne,
                fontWeight = FontWeight.Normal,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(10.dp))
            PasswordTextField(
                value = masterPassword,
                onValueChange = { masterPassword = it },
                label = "Master Password",
                modifier = Modifier.height(40.dp).width(360.dp)
            )
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    enabled = true,
                    onClick = {
                        viewModel.setMasterPassword(masterPassword)
                        dialogState.value = false
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
                        text = "Apply",
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