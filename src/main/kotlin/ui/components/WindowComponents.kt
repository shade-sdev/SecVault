package ui.components

import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import ui.theme.secondary
import kotlin.system.exitProcess

@Composable
fun CloseButton() {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    Button(
        onClick = { exitProcess(0) },
        modifier = Modifier.size(30.dp)
            .hoverable(interactionSource),
        shape = RoundedCornerShape(topEnd = 10.dp),
        colors = ButtonColors(
            containerColor = if (isHovered) Color(0xFFb91919) else secondary,
            contentColor = Color.White,
            disabledContentColor = secondary,
            disabledContainerColor = secondary
        ),
        contentPadding = PaddingValues(0.dp)
    )
    {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = "Setting button",
            modifier = Modifier.size(15.dp),
            tint = Color.White
        )
    }
}