package ui.components.secvault.passwordlayout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.components.RoundedFilledTextField
import ui.theme.Font
import ui.theme.PasswordColors

@Composable
fun PasswordFilterHeader() {
    val options = listOf("Name", "Favourite", "Created")
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(options[0]) }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth().fillMaxHeight()
    )
    {
        Row(
            modifier = Modifier.fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            Column(modifier = Modifier.weight(8.5f).fillMaxHeight().fillMaxWidth()) {
                RoundedFilledTextField(
                    placeholder = "Search Passwords",
                    modifier = Modifier.height(36.dp).fillMaxWidth()
                )
            }

            Column(
                modifier = Modifier.weight(1.5f).fillMaxHeight().fillMaxWidth(),
                horizontalAlignment = Alignment.End
            ) {
                FilledIconButton(
                    shape = IconButtonDefaults.filledShape,
                    onClick = {},
                    enabled = true,
                    modifier = Modifier.height(36.dp).width(36.dp),
                    colors = IconButtonColors(
                        containerColor = Color(0xFFFB8C00),
                        contentColor = Color.White,
                        disabledContentColor = Color(0xFFFB8C00),
                        disabledContainerColor = Color(0xFFFB8C00)
                    )
                )
                {
                    Icon(Icons.Outlined.Add, contentDescription = "Localized description")

                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth()
                    .fillMaxHeight()
                    .weight(1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
        )
        {
            Column(
                modifier = Modifier.weight(6f)
                        .fillMaxHeight()
                        .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
            )
            {
                Text(
                    modifier = Modifier.align(Alignment.Start),
                    text = "All Passwords",
                    color = Color.White,
                    fontFamily = Font.RussoOne,
                    fontWeight = FontWeight.Normal,
                    fontSize = 18.sp,
                )
            }

            Column(
                modifier = Modifier.weight(5f)
                        .fillMaxHeight()
                        .fillMaxWidth(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.Center,
            )
            {
                Row(
                    modifier = Modifier.fillMaxHeight().fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically,
                )
                {
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "Sort By",
                            color = Color.White,
                            fontFamily = Font.RobotoThin,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                        )
                    }
                    Spacer(Modifier.width(10.dp))
                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.End
                    )
                    {
                        Row(
                            modifier = Modifier
                                    .clickable(
                                        onClick = { expanded = !expanded },
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null
                                    ),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End,
                        )
                        {
                            Text(selectedOption, fontFamily = Font.RussoOne, fontSize = 10.sp, color = Color.White)
                            Icon(
                                Icons.Default.ArrowDropDown,
                                contentDescription = "Dropdown Menu",
                                tint = Color.White,
                                modifier = Modifier.padding(PaddingValues(top = 4.dp))
                            )
                            DropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                modifier = Modifier.background(PasswordColors.primary)
                            ) {
                                options.forEach { selectionOption ->
                                    DropdownMenuItem(onClick = {
                                        selectedOption = selectionOption
                                        expanded = false
                                    }) {
                                        Text(
                                            text = selectionOption,
                                            fontFamily = Font.RussoOne,
                                            fontSize = 10.sp,
                                            color = Color.White
                                        )
                                    }
                                }
                            }
                        }

                    }
                }
            }
        }
    }
}