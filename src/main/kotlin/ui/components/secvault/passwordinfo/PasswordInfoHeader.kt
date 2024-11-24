package ui.components.secvault.passwordinfo

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import core.models.DefaultMenuItem
import core.models.FormType.MODIFIATION
import ui.components.forms.passwordmgnt.CreditCardForm
import ui.screens.PasswordMgntScreen
import ui.theme.Font
import ui.theme.PasswordColors
import viewmodel.SecVaultScreenModel

@Composable
fun PasswordInfoHeader(screenModel: SecVaultScreenModel) {
    val selectedCredential by screenModel.selectedCredential.collectAsState()
    val selectedMenu by screenModel.selectedMenuItem.collectAsState()

    val title by remember(selectedCredential, selectedMenu) {
        mutableStateOf(selectedCredential.getTitle(selectedMenu))
    }

    val description by remember(selectedCredential, selectedMenu) {
        mutableStateOf(selectedCredential.getDescription(selectedMenu))
    }

    Row(
        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top
    )
    {
        Column(
            modifier = Modifier.weight(7f).fillMaxHeight().fillMaxWidth(),
            horizontalAlignment = Alignment.Start
        )
        {
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            )
            {

                Column() {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "User Icon",
                        modifier = Modifier.size(56.dp),
                        tint = Color.White
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy((-6).dp, Alignment.CenterVertically)) {

                    Row() {
                        Text(
                            text = title,
                            fontSize = 20.sp,
                            fontFamily = Font.RussoOne,
                            color = Color.White
                        )
                    }

                    Row() {
                        Text(
                            text = description,
                            fontSize = 14.sp,
                            fontFamily = Font.Aldrich,
                            color = PasswordColors.outline
                        )
                    }

                }

            }
        }

        Column(
            modifier = Modifier.weight(3f)
                    .fillMaxHeight()
                    .fillMaxWidth()
                    .padding(PaddingValues(top = 4.dp)),
            horizontalAlignment = Alignment.End
        )
        {
            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Column() {
                    IconButton(
                        onClick = {
                            when (selectedMenu) {
                                DefaultMenuItem.PASSWORDS,
                                DefaultMenuItem.CREDIT_CARD,
                                DefaultMenuItem.NOTES -> {
                                    screenModel.favorite(selectedCredential.getId()!!)
                                }
                            }
                        },
                        enabled = selectedCredential.isSelected()
                    )
                    {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Favorite Icon",
                            modifier = Modifier.size(24.dp),
                            tint = if (selectedCredential.isFavorite()) Color.Yellow else Color.White
                        )
                    }
                }

                Column() {
                    val interactionSource = remember { MutableInteractionSource() }
                    val isHovered by interactionSource.collectIsHoveredAsState()
                    val navigator = LocalNavigator.current

                    OutlinedButton(
                        onClick = {
                            when (selectedMenu) {
                                DefaultMenuItem.PASSWORDS -> {
                                    navigator?.push(PasswordMgntScreen(selectedCredential.password, MODIFIATION))
                                }

                                DefaultMenuItem.CREDIT_CARD -> {
                                    navigator?.push(CreditCardForm(selectedCredential.creditCard, MODIFIATION))
                                }

                                DefaultMenuItem.NOTES -> TODO()
                            }
                        },
                        enabled = selectedCredential.isSelected(),
                        modifier = Modifier.size(height = 28.dp, width = 62.dp)
                                .hoverable(interactionSource),
                        shape = RoundedCornerShape(size = 4.dp),
                        contentPadding = PaddingValues(4.dp),
                        border = BorderStroke(2.dp, color = Color.White),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (isHovered) Color.White else Color.Transparent,
                            disabledContentColor = Color.White,
                            disabledContainerColor = if (isHovered) Color.White else Color.Transparent,
                        )

                    )
                    {
                        Text(
                            text = "Edit",
                            fontSize = 12.sp,
                            fontFamily = Font.RussoOne,
                            color = if (isHovered) PasswordColors.tertiary else Color.White
                        )
                    }
                }
            }
        }
    }

}