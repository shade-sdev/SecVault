package ui.components.secvault.passwordlayout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import viewmodel.SecVaultScreenModel

@Composable
fun PasswordLayout(screenModel: SecVaultScreenModel) {
    val passwordItems by screenModel.passwordItems.collectAsState()

    Column(
        modifier = Modifier.padding(PaddingValues(start = 20.dp, end = 20.dp, top = 24.dp, bottom = 20.dp))
    )
    {

        Row(
            modifier = Modifier.fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1.6f)
        )
        {
            PasswordFilterHeader(screenModel)
        }

        Row(
            modifier = Modifier.fillMaxWidth()
                    .fillMaxHeight()
                    .weight(8.4f)
        )
        {
            LazyVerticalGrid(
                columns = GridCells.Fixed(1),
                modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(2.dp),
            )
            {
                items(passwordItems) { item ->
                    PasswordItem(item)
                }
            }

        }
    }
}