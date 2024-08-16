package ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.models.NotificationType
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
fun NotificationFactory(
    message: String,
    visible: Boolean,
    onDismiss: () -> Unit,
    type: NotificationType,
    durationMillis: Long = 3000
) {
    TopRightNotification(
        message = message,
        visible = visible,
        onDismiss = onDismiss,
        backgroundColor = type.color,
        durationMillis = durationMillis
    )
}

@Composable
fun TopRightNotification(
    message: String,
    visible: Boolean,
    onDismiss: () -> Unit,
    backgroundColor: Color,
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
                        .fillMaxHeight(0.12f)
                        .fillMaxWidth(0.3f)
                        .padding(18.dp),
                shape = RoundedCornerShape(8.dp),
                shadowElevation = 4.dp,
                color = backgroundColor
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.padding(PaddingValues(start = 12.dp))
                ) {
                    Text(
                        modifier = Modifier.weight(0.8f),
                        text = message,
                        fontSize = 12.sp,
                        fontFamily = Font.RobotoRegular,
                        color = Color.White,
                        lineHeight = 12.sp,
                        maxLines = 4
                    )
                    IconButton(onClick = onDismiss, modifier = Modifier.weight(0.2f)) {
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

@Composable
fun HorizontalSpacer(
    thickness: Dp = 0.2.dp,
    color: Color = Color.Gray,
    width: Float = 0.8f,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    )
    {
        HorizontalDivider(
            color = color,
            thickness = thickness,
            modifier = Modifier.fillMaxWidth(width)
        )
    }
}

@Composable
fun ShimmerShape(modifier: Modifier, shape: Shape, radius: Float?) {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f)
    )

    val transition = rememberInfiniteTransition()
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1200, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = modifier.clip(shape)
                    .drawBehind {
                        val brush = Brush.linearGradient(
                            colors = shimmerColors,
                            start = Offset.Zero,
                            end = Offset(x = translateAnim.value, y = translateAnim.value)
                        )
                        if (shape == CircleShape)
                            drawCircle(brush = brush, radius = radius!!)
                        else
                            drawRect(brush = brush, size = Size(size.width, size.height))
                    }
        )
    }
}