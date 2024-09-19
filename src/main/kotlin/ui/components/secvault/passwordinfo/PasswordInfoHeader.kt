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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import ui.screens.LoginScreen
import ui.theme.Font
import ui.theme.PasswordColors

@Composable
fun PasswordInfoHeader() {

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
                            text = "Spotify",
                            fontSize = 20.sp,
                            fontFamily = Font.RussoOne,
                            color = Color.White
                        )
                    }

                    Row() {
                        Text(
                            text = "Shade@Shade.ga",
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
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "User Icon",
                        modifier = Modifier.size(24.dp),
                        tint = Color.White
                    )
                }

                Column() {
                    val interactionSource = remember { MutableInteractionSource() }
                    val isHovered by interactionSource.collectIsHoveredAsState()
                    val navigator = LocalNavigator.current

                    OutlinedButton(
                        onClick = { navigator?.push(LoginScreen()) },
                        modifier = Modifier.size(height = 28.dp, width = 62.dp)
                                .hoverable(interactionSource),
                        shape = RoundedCornerShape(size = 4.dp),
                        contentPadding = PaddingValues(4.dp),
                        border = BorderStroke(2.dp, color = Color.White),
                        colors = ButtonDefaults.outlinedButtonColors(
                            containerColor = if (isHovered) Color.White else Color.Transparent,
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