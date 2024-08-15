package ui.components.secvault.passwordlayout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import repository.password.projection.PasswordSummary
import ui.theme.Font
import ui.theme.PasswordColors

@Composable
fun PasswordItem(passwordSummary: PasswordSummary) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    Row(
        modifier = Modifier.height(60.dp).fillMaxWidth()
                .background(
                    if (isHovered) PasswordColors.tertiary else Color.Transparent,
                    shape = RoundedCornerShape(6.dp)
                )
                .padding(PaddingValues(start = 5.dp, end = 5.dp))
                .clickable(onClick = {}, indication = null, interactionSource = interactionSource)
                .hoverable(interactionSource),
        verticalAlignment = Alignment.CenterVertically
    )
    {

        Column(modifier = Modifier.weight(1f)) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "User Icon",
                modifier = Modifier.size(38.dp),
                tint = Color.White
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column(
            modifier = Modifier.weight(8.5f).fillMaxHeight().fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy((-8).dp, Alignment.CenterVertically)
        )
        {

            Row {
                Text(
                    text = passwordSummary.name,
                    fontSize = 16.sp,
                    fontFamily = Font.RussoOne,
                    color = Color.White
                )
            }

            Row {
                Text(
                    text = passwordSummary.email ?: passwordSummary.username ?: "",
                    fontSize = 12.sp,
                    fontFamily = Font.RobotoThin,
                    color = PasswordColors.outline
                )
            }

        }

        Column(
            modifier = Modifier.weight(0.5f).fillMaxHeight().fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "User Icon",
                modifier = Modifier.size(38.dp),
                tint = if (passwordSummary.favorite) Color.Yellow else Color.White
            )
        }
    }
}