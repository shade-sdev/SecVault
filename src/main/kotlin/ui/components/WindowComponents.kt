package ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsHoveredAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.*
import androidx.compose.ui.window.Dialog
import core.models.NotificationType
import kotlinx.coroutines.delay
import ui.theme.Font
import ui.theme.PasswordColors
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
fun OutlineTextButton(
    modifier: Modifier,
    cornerSize: Dp,
    fontSize: TextUnit,
    text: String,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isHovered by interactionSource.collectIsHoveredAsState()

    OutlinedButton(
        onClick = onClick,
        modifier = modifier.hoverable(interactionSource),
        shape = RoundedCornerShape(size = cornerSize),
        contentPadding = PaddingValues(4.dp),
        border = BorderStroke(1.dp, color = Color.White),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = if (isHovered) Color.White else Color.Transparent,
        )

    )
    {
        Text(
            text = text,
            fontSize = fontSize,
            fontFamily = Font.RussoOne,
            color = if (isHovered) PasswordColors.tertiary else Color.White
        )
    }
}

@Composable
fun SecVaultDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier,
    roundedSize: Dp,
    backgroundColor: Color,
    content: @Composable () -> Unit,
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(roundedSize),
            content = content,
            backgroundColor = backgroundColor
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
                fontFamily = Font.RobotoRegular,
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

@Composable
fun <T> MultiSelectDropdown(
    items: Collection<T>,
    selectedItems: Collection<T>,
    onItemSelect: (T) -> Unit,
    onItemDeselect: (T) -> Unit,
    itemToString: (T) -> String,
    modifier: Modifier = Modifier,
    placeholder: String = "Select items...",
    backgroundColor: Color,
    foregroundColor: Color
) {
    var expanded by remember { mutableStateOf(false) }
    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    Box(modifier = modifier) {
        Column {
            OutlinedTextField(
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledTextColor = Color.LightGray,
                    unfocusedContainerColor = backgroundColor,
                    focusedContainerColor = backgroundColor,
                    focusedTextColor = foregroundColor,
                    unfocusedTextColor = foregroundColor,
                    unfocusedLabelColor = foregroundColor,
                    focusedLabelColor = foregroundColor
                ),
                shape = RoundedCornerShape(8.dp),
                value = if (selectedItems.isEmpty()) placeholder else selectedItems.sortedBy(itemToString)
                        .joinToString(", ") { itemToString(it) },
                onValueChange = { },
                textStyle = TextStyle(color = foregroundColor, fontSize = 12.sp, fontFamily = Font.RussoOne),
                modifier = Modifier
                        .fillMaxWidth()
                        .height(45.dp)
                        .onSizeChanged { textFieldSize = it.toSize() },
                placeholder = { Text(placeholder, color = Color.White, fontSize = 12.sp) },
                trailingIcon = {
                    IconButton(onClick = { expanded = !expanded }) {
                        Icon(
                            Icons.Default.ArrowDropDown,
                            contentDescription = if (expanded) "Close" else "Open"
                        )
                    }
                }
            )
        }

        // Dropdown menu
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                    .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                    .heightIn(max = 250.dp)
        ) {
            Box(
                modifier = Modifier
                        .heightIn(max = 250.dp)
                        .padding(vertical = 4.dp)
            ) {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    items.forEach { item ->
                        val isSelected = selectedItems.contains(item)
                        DropdownMenuItem(
                            onClick = {
                                if (isSelected) {
                                    onItemDeselect(item)
                                } else {
                                    onItemSelect(item)
                                }
                            },
                            modifier = Modifier.height(48.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = isSelected,
                                    onCheckedChange = null
                                )
                                Text(itemToString(item), color = foregroundColor, fontSize = 12.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}