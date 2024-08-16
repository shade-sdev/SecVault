package ui.components.secvault.passwordlayout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.models.UiState
import viewmodel.SecVaultScreenModel

@Composable
fun PasswordLayout(screenModel: SecVaultScreenModel) {
    val passwordItems by screenModel.passwordItems.collectAsState()
    val secVaultState by screenModel.secVaultState.collectAsState()

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
            LazyColumn(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(2.dp),
            )
            {
                when (secVaultState) {
                    is UiState.Loading -> {
                        items(24) {
                            PasswordShimmerItem()
                        }
                    }

                    is UiState.Success,
                    is UiState.Error,
                    is UiState.Idle -> {
                        if (passwordItems.isNotEmpty()) {
                            items(passwordItems) { item ->
                                PasswordItem(item)
                            }
                        } else {
                            items(24) {
                                PasswordShimmerItem()
                            }
                        }
                    }
                }
            }

        }
    }
}