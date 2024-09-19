package ui.components

import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
import ui.theme.PasswordColors
import ui.theme.secondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "",
    icon: ImageVector,
) {
    val interactionSource = remember { MutableInteractionSource() }

    Surface(
        modifier = modifier,
        color = secondary,
        shape = RoundedCornerShape(8.dp),
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
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
                value = value,
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
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "",
) {
    val interactionSource = remember { MutableInteractionSource() }
    var passwordVisible by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier,
        color = secondary,
        shape = RoundedCornerShape(8.dp),
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
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
                value = value,
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoundedFilledTextField(
    modifier: Modifier = Modifier,
    placeholder: String = "",
) {
    var user by remember { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }
    BasicTextField(
        value = user,
        onValueChange = { user = it },
        interactionSource = interactionSource,
        modifier = modifier,
        textStyle = TextStyle(
            fontFamily = Font.RussoOne,
            color = PasswordColors.outlineVariant,
            textAlign = TextAlign.Start,
            fontSize = 13.sp
        ),
        singleLine = true
    ) { innerTextField ->

        TextFieldDefaults.DecorationBox(
            innerTextField = innerTextField,
            placeholder = {
                Text(
                    placeholder,
                    fontSize = 13.sp,
                    fontFamily = Font.RussoOne,
                    modifier = Modifier.padding(PaddingValues(bottom = 3.dp))
                )
            },
            value = user,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "",
                    tint = PasswordColors.outlineVariant,
                    modifier = Modifier.width(20.dp).height(20.dp)

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
                focusedTextColor = PasswordColors.outlineVariant,
                unfocusedTextColor = PasswordColors.outlineVariant,
                focusedPlaceholderColor = PasswordColors.outlineVariant,
                unfocusedPlaceholderColor = PasswordColors.outlineVariant
            ),
            shape = RoundedCornerShape(20.dp),
            contentPadding = PaddingValues(0.dp)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnderLineTextFiled(
    modifier: Modifier = Modifier,
    label: String,
    field: String,
    onFieldChange: (String) -> Unit,
    isPassword: Boolean = false,
) {
    val interactionSource = remember { MutableInteractionSource() }
    var passwordVisible by remember { mutableStateOf(false) }

    BasicTextField(
        value = field,
        onValueChange = onFieldChange,
        interactionSource = interactionSource,
        modifier = modifier,
        textStyle = TextStyle(
            fontFamily = Font.RussoOne,
            color = Color.White,
            textAlign = TextAlign.Start,
            fontSize = 12.sp
        ),
        singleLine = true,
        cursorBrush = SolidColor(Color.White),
        visualTransformation = if (!isPassword || passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
    ) { innerTextField ->
        TextFieldDefaults.DecorationBox(
            interactionSource = interactionSource,
            innerTextField = innerTextField,
            enabled = true,
            value = field,
            singleLine = true,
            visualTransformation = if (!isPassword || passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            label = {
                Text(
                    label,
                    fontFamily = Font.RussoOne,
                    textAlign = TextAlign.Start,
                    fontSize = 10.sp,
                    color = Color.Gray
                )
            },
            trailingIcon = {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp, Alignment.End),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        if (isPassword) {
                            IconToggleButton(
                                checked = passwordVisible,
                                onCheckedChange = { passwordVisible = it }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Default.VisibilityOff
                                    else Icons.Default.Visibility,
                                    contentDescription = "",
                                    modifier = Modifier.size(width = 15.dp, height = 15.dp),
                                    tint = Color.Gray
                                )
                            }
                        }
                    }
                    Column {
                        Icon(
                            imageVector = Icons.Default.ContentCopy,
                            contentDescription = "",
                            tint = Color.Gray,
                            modifier = Modifier.size(width = 15.dp, height = 15.dp)
                        )
                    }
                    if (isPassword) {
                        Spacer(modifier = Modifier.width(12.dp))
                    }
                }
            },
            contentPadding = TextFieldDefaults.contentPaddingWithLabel(0.dp),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color(0xFF333333),
                unfocusedIndicatorColor = Color(0xFF333333),
                disabledTextColor = Color.LightGray,
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
        )
    }
}