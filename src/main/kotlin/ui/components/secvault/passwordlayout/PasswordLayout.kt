package ui.components.secvault.passwordlayout

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.models.CredentialDisplay
import core.models.DefaultMenuItem
import core.models.UiState
import viewmodel.SecVaultScreenModel
import java.util.*

@Composable
fun PasswordLayout(screenModel: SecVaultScreenModel) {
    val menuType by screenModel.selectedMenuItem.collectAsState()
    val creditCards by screenModel.creditCardItems.collectAsState()
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
                        when (menuType) {
                            DefaultMenuItem.PASSWORDS -> {
                                if (passwordItems.isNotEmpty()) {
                                    items(passwordItems) { item ->
                                        PasswordItem(
                                            CredentialDisplay(
                                                item.id,
                                                item.name,
                                                if (item.email?.isEmpty() == true) item.username!! else item.email!!,
                                                favorite = item.favorite,
                                            ),
                                            onClick = { id: UUID -> screenModel.loadSelectedCredential(id) }
                                        )
                                    }
                                } else {
                                    items(24) {
                                        PasswordShimmerItem()
                                    }
                                }
                            }

                            DefaultMenuItem.CREDIT_CARD -> {
                                if (creditCards.isNotEmpty()) {
                                    items(creditCards) { item ->
                                        PasswordItem(
                                            CredentialDisplay(
                                                item.id,
                                                item.name,
                                                item.number,
                                                favorite = item.favorite
                                            ),
                                            onClick = { id: UUID -> screenModel.loadSelectedCredential(id) }
                                        )
                                    }
                                } else {
                                    items(24) {
                                        PasswordShimmerItem()
                                    }
                                }
                            }

                            DefaultMenuItem.NOTES -> TODO()
                        }
                    }
                }
            }

        }
    }
}