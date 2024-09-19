package ui.components.secvault.passwordinfo

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PasswordInfo() {
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
            PasswordInfoHeader()
        }

        Row(
            modifier = Modifier.weight(5f)
                    .fillMaxWidth()
                    .fillMaxHeight()
        )
        {
            PasswordForm()
        }

        Row(
            modifier = Modifier.weight(6f)
                    .fillMaxWidth()
                    .fillMaxHeight()
        )
        {
            PasswordMisc()
        }
    }
}