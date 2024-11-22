package ui.components.secvault.passwordinfo

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.models.DefaultMenuItem
import viewmodel.SecVaultScreenModel

@Composable
fun PasswordInfo(screenModel: SecVaultScreenModel) {
    val menuItem = screenModel.selectedMenuItem.collectAsState()

    val credentialFormWeight = if (menuItem.value == DefaultMenuItem.PASSWORDS) 5f else 10f
    val miscWeight = if (menuItem.value == DefaultMenuItem.PASSWORDS) 6f else 1f

    Column(
        modifier = Modifier
                .padding(PaddingValues(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 20.dp))
                .fillMaxWidth()
                .fillMaxHeight()
    )
    {

        Row(
            modifier = Modifier.weight(1.5f)
                    .fillMaxWidth()
                    .fillMaxHeight()
        )
        {
            PasswordInfoHeader(screenModel)
        }

        Row(
            modifier = Modifier.weight(credentialFormWeight)
                    .fillMaxWidth()
                    .fillMaxHeight()
        )
        {
            PasswordForm(screenModel)
        }

        Row(
            modifier = Modifier.weight(miscWeight)
                    .fillMaxWidth()
                    .fillMaxHeight()
        )
        {
            if (menuItem.value == DefaultMenuItem.PASSWORDS){
                PasswordMisc()
            }
        }
    }
}