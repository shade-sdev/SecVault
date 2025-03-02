package ui.components.secvault.sidebar

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import viewmodel.SecVaultScreenModel

@Composable
fun SideBar(screenModel: SecVaultScreenModel) {
    Column(
        modifier = Modifier.padding(PaddingValues(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 20.dp))
    )
    {

        Row(
            modifier = Modifier.fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1.6f)
        )
        {
            Header()
        }

        Row(
            modifier = Modifier.fillMaxWidth()
                    .fillMaxHeight()
                    .weight(7f)
        )
        {

            SideBarMenu(screenModel)
        }

        Row(
            modifier = Modifier.fillMaxWidth()
                    .fillMaxHeight()
                    .weight(0.8f)
        )
        {
            Footer()
        }
    }
}