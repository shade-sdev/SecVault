package ui.components.setting

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FileUpload
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.components.HorizontalSpacer
import ui.theme.Font
import ui.theme.secondary
import ui.theme.tertiary
import viewmodel.SettingScreenModel

@Preview
@Composable
fun SettingScreenContent(screenModel: SettingScreenModel) {

    Column(
        modifier = Modifier.fillMaxSize()
                .background(color = tertiary)
    ) {
        Row(
            modifier = Modifier.weight(1.5f)
                    .fillMaxSize()
                    .background(color = secondary)
                    .padding(26.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically)
            ) {
                Text(
                    text = "Settings",
                    color = Color.White,
                    fontFamily = Font.RussoOne,
                    fontWeight = FontWeight.Normal,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = "Manage your account settings.",
                    color = Color.White,
                    fontFamily = Font.RobotoThin,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                )
            }

        }

        Row(
            modifier = Modifier.weight(8.5f)
                    .fillMaxSize()
        ) {
            Column(modifier = Modifier.fillMaxSize()) {

                Row(
                    modifier = Modifier
                            .fillMaxHeight(0.2f)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                                .weight(3f)
                                .padding(PaddingValues(horizontal = 50.dp, vertical = 20.dp)),
                        verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically)
                    ) {
                        Text(
                            text = "Google Drive Integration",
                            color = Color.White,
                            fontFamily = Font.RobotoBold,
                            fontWeight = FontWeight.Normal,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center,
                        )
                        Text(
                            text = "Setup Google Drive for Backups",
                            color = Color.White,
                            fontFamily = Font.RobotoThin,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            textAlign = TextAlign.Center,
                        )
                    }

                    Column(
                        modifier = Modifier.fillMaxSize()
                                .weight(7f)
                                .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterVertically)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            FilledIconButton(
                                shape = IconButtonDefaults.filledShape,
                                onClick = { screenModel.openDialog() },
                                enabled = true,
                                modifier = Modifier.height(35.dp).width(100.dp),
                                colors = IconButtonColors(
                                    containerColor = Color.White,
                                    contentColor = tertiary,
                                    disabledContentColor = Color.Gray,
                                    disabledContainerColor = Color.Gray
                                )
                            ) {
                                Icon(Icons.Outlined.FileUpload, contentDescription = "Localized description")
                            }

                            Button(
                                onClick = { screenModel.authenticateGoogleDrive() },
                                modifier = Modifier.height(35.dp).width(100.dp),
                                colors = ButtonColors(
                                    containerColor = Color.White,
                                    contentColor = tertiary,
                                    disabledContentColor = Color.Gray,
                                    disabledContainerColor = Color.Gray,
                                )
                            )
                            {
                                Text(
                                    text = "Login",
                                    fontStyle = FontStyle.Normal,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 12.sp,
                                    fontFamily = Font.RussoOne
                                )
                            }
                        }

                    }
                }
                Row(
                    modifier = Modifier.fillMaxHeight(0.1f).fillMaxWidth()

                ) {
                    HorizontalSpacer(width = 0.8f)
                }
            }
        }
    }
}