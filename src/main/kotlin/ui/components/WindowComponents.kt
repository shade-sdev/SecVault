package ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import ui.theme.Font
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

@Composable
fun LoadingScreen(
    modifier: Modifier = Modifier,
    text: String = "Loading...",
    backgroundColor: Color = Color(0xFF121212),
    indicatorColor: Color = Color.White,
    textColor: Color = Color.White
) {
    Box(
        modifier = modifier
                .fillMaxSize()
                .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(
                color = indicatorColor,
                trackColor = Color(0xFF1C1520),
                strokeWidth = 6.dp,
                strokeCap = StrokeCap.Round,
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text,
                color = textColor,
                fontSize = 16.sp
            )
        }
    }
}

@Composable
fun TopRightNotification(
    message: String,
    visible: Boolean,
    onDismiss: () -> Unit,
    durationMillis: Long = 3000
) {
    if (visible) {
        Box(
            modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            Surface(
                modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .padding(18.dp),
                shape = RoundedCornerShape(8.dp),
                shadowElevation = 4.dp,
                color = Color.Red
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.padding(PaddingValues(start = 12.dp))
                ) {
                    Text(
                        text = message,
                        fontSize = 12.sp,
                        fontFamily = Font.RussoOne,
                        color = Color.White
                    )
                    IconButton(onClick = { onDismiss() }) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Close",
                            tint = Color.White
                        )
                    }
                }
            }
        }

        LaunchedEffect(Unit) {
            delay(durationMillis)
            onDismiss()
        }
    }
}
