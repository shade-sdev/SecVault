package ui.components.secvault.sidebar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.models.DefaultMenuItem
import ui.components.HorizontalSpacer
import ui.theme.Font
import ui.theme.PasswordColors
import viewmodel.SecVaultScreenModel

@Composable
fun SideBarMenu(screenModel: SecVaultScreenModel) {
    val menuItems by screenModel.menuItems.collectAsState()
    val selectedMenuItem by screenModel.selectedMenuItem.collectAsState()

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalSpacer()
        Spacer(Modifier.height(15.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically),
        )

        {
            Text(
                modifier = Modifier.align(Alignment.Start),
                text = "VAULT",
                color = Color.White,
                fontFamily = Font.RussoOne,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
            )
            Row(
                modifier = Modifier.fillMaxWidth()
            )
            {
                SideBarMenuSection(menuItems, selectedMenuItem, screenModel::selectMenuItem)
            }

            Text(
                modifier = Modifier.align(Alignment.Start),
                text = "CATEGORIES",
                color = Color.White,
                fontFamily = Font.RussoOne,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
            )
            Row(
                modifier = Modifier.fillMaxWidth()
            )
            {
                SideBarMenuSection(menuItems, selectedMenuItem, screenModel::selectMenuItem)
            }
        }
    }
}

@Composable
fun SideBarMenuSection(menuItems: List<DefaultMenuItem>, selectedMenuItem: DefaultMenuItem, onMenuClick: (DefaultMenuItem) -> Unit) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(4.dp, alignment = Alignment.CenterVertically)
    )
    {
        items(menuItems) { menuItem ->
            SideBarMenuItem(menuItem.value,
                menuItem.value == selectedMenuItem.value,
                Icons.Default.Security,
                onClick = { onMenuClick(menuItem) })
        }
    }

}

@Composable
fun SideBarMenuItem(
    text: String,
    selected: Boolean,
    icon: ImageVector,
    onClick: () -> Unit = {},
    backgroundColor: Color = Color.Transparent,
    hoverColor: Color = PasswordColors.onSurface,
) {

    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable(onClick = onClick, indication = null, interactionSource = interactionSource)
            .hoverable(interactionSource)
            .height(34.dp)
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                if (isHovered || selected) hoverColor else backgroundColor,
                shape = RoundedCornerShape(CornerSize(10.dp))
            )

    ) {
        Spacer(modifier = Modifier.width(2.dp))
        Icon(
            imageVector = icon,
            contentDescription = "",
            modifier = Modifier.size(16.dp),
            tint = if (isHovered || selected) Color.White else PasswordColors.outline
        )
        Text(
            text = text,
            color = if (isHovered) Color.White else PasswordColors.outline,
            fontSize = 12.sp,
            fontFamily = Font.RussoOne,
            fontWeight = FontWeight.Normal,
        )
    }
}
