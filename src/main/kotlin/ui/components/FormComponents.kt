package ui.components

import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.theme.Font
import ui.theme.secondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormTextField(
    modifier: Modifier = Modifier,
    label: String = "",
    icon: ImageVector,
) {
    var user by remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }

    Surface(
        modifier = modifier,
        color = secondary,
        shape = RoundedCornerShape(8.dp),
    ) {
        BasicTextField(
            value = user,
            onValueChange = { user = it },
            interactionSource = interactionSource,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 14.dp, bottom = 3.dp),
            textStyle = TextStyle(
                fontFamily = Font.RussoOne,
                color = Color.White,
                textAlign = TextAlign.Start,
                fontSize = 12.sp
            ),
            singleLine = true,
            cursorBrush = SolidColor(Color.White),
        ) { innerTextField ->

            TextFieldDefaults.DecorationBox(
                innerTextField = innerTextField,
                label = {
                    Text(
                        label,
                        fontSize = 10.sp,
                        fontFamily = Font.RussoOne,
                    )
                },
                value = user,
                trailingIcon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = "",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                },
                enabled = true,
                interactionSource = interactionSource,
                singleLine = true,
                visualTransformation = VisualTransformation.None,
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledTextColor = Color.LightGray,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    focusedLabelColor = Color.Gray
                ),
                contentPadding = PaddingValues(0.dp),
                container = {}
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    label: String = "",
) {
    var password by remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }
    var passwordVisible by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier,
        color = secondary,
        shape = RoundedCornerShape(8.dp),
    ) {
        BasicTextField(
            value = password,
            onValueChange = { password = it },
            interactionSource = interactionSource,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 14.dp, bottom = 3.dp),
            textStyle = TextStyle(
                fontFamily = Font.RussoOne,
                color = Color.White,
                textAlign = TextAlign.Start,
                fontSize = 12.sp
            ),
            singleLine = true,
            cursorBrush = SolidColor(Color.White),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
        ) { innerTextField ->

            TextFieldDefaults.DecorationBox(
                innerTextField = innerTextField,
                label = {
                    Text(
                        label,
                        fontSize = 10.sp,
                        fontFamily = Font.RussoOne,
                    )
                },
                value = password,
                trailingIcon = {
                    IconToggleButton(
                        checked = passwordVisible,
                        onCheckedChange = { passwordVisible = it },
                        modifier = Modifier.hoverable(interactionSource)
                            .pointerHoverIcon(PointerIcon.Hand)
                    )
                    {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.VisibilityOff
                            else Icons.Default.Visibility,
                            contentDescription = "",
                            modifier = Modifier.size(width = 15.dp, height = 15.dp),
                            tint = Color.White
                        )
                    }
                },
                enabled = true,
                interactionSource = interactionSource,
                singleLine = true,
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledTextColor = Color.LightGray,
                    unfocusedContainerColor = Color.White,
                    focusedContainerColor = Color.White,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    focusedLabelColor = Color.Gray
                ),
                contentPadding = PaddingValues(0.dp),
                container = {}
            )
        }
    }
}