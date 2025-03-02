package ui.components.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.outlined.FileUpload
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import core.external.google.GoogleAppState
import core.security.SecurityContext
import ui.components.HorizontalSpacer
import ui.theme.Font
import ui.theme.secondary
import ui.theme.tertiary
import viewmodel.SettingScreenModel

@Composable
fun SettingScreenContent(screenModel: SettingScreenModel) {

    val googleAppState by screenModel.googleAppState.collectAsState()
    val navigator = LocalNavigator.current
    Column(
        modifier = Modifier.fillMaxSize()
            .background(color = tertiary)
    ) {
        Row(
            modifier = Modifier.weight(1.5f)
                .fillMaxSize()
                .background(color = secondary),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.padding(26.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically),
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

            Column(
                modifier = Modifier.width(40.dp).background(Color.LightGray),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.End
            ) {
                IconButton(onClick = { navigator?.pop() }, modifier = Modifier.size(40.dp)) {
                    Icon(
                        Icons.Default.ChevronLeft,
                        contentDescription = "Back",
                        tint = tertiary
                    )
                }
            }
        }

        Row(
            modifier = Modifier.weight(8.5f)
                .fillMaxSize()
                .fillMaxHeight()
        ) {
            Column(
                modifier = Modifier.fillMaxSize().fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {

                GoogleDriveConfigComponent(screenModel, googleAppState)

                Row(
                    modifier = Modifier.height(1.dp)
                        .fillMaxWidth()

                ) {
                    HorizontalSpacer(width = 0.8f)
                }

                ImportPasswordFromExcelComponent(screenModel)

                Row(
                    modifier = Modifier.height(1.dp)
                        .fillMaxWidth()

                ) {
                    HorizontalSpacer(width = 0.8f)
                }

            }
        }
    }
}

@Composable
fun ImportPasswordFromExcelComponent(screenModel: SettingScreenModel) {
    Row(
        modifier = Modifier
            .height(100.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .weight(3f)
                .padding(PaddingValues(horizontal = 50.dp)),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Import Passwords",
                color = Color.White,
                fontFamily = Font.RobotoBold,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
            )
            Text(
                text = "Import passwords from an excel file",
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
                horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilledIconButton(
                    shape = IconButtonDefaults.filledShape,
                    onClick = { screenModel.onImportExcelDialog() },
                    enabled = true,
                    modifier = Modifier.height(35.dp)
                        .width(100.dp),
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
                    onClick = { screenModel.onExportExcelTemplateDialog() },
                    modifier = Modifier.height(35.dp)
                        .width(200.dp),
                    colors = ButtonColors(
                        containerColor = Color.White,
                        contentColor = tertiary,
                        disabledContentColor = Color.Gray,
                        disabledContainerColor = Color.Gray,
                    )
                )
                {
                    Text(
                        text = "Download Template",
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        fontFamily = Font.RussoOne
                    )
                }
            }
        }
    }
}

@Composable
fun GoogleDriveConfigComponent(screenModel: SettingScreenModel, googleAppState: GoogleAppState?) {
    Row(
        modifier = Modifier
            .height(100.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .weight(3f)
                .padding(PaddingValues(horizontal = 50.dp)),
            verticalArrangement = Arrangement.Center
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
                horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.Start),
                verticalAlignment = Alignment.CenterVertically
            ) {
                FilledIconButton(
                    shape = IconButtonDefaults.filledShape,
                    onClick = { screenModel.onJsonDialog() },
                    enabled = true,
                    modifier = Modifier.height(35.dp)
                        .width(100.dp),
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
                    modifier = Modifier.height(35.dp)
                        .width(100.dp),
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
                Button(
                    onClick = { screenModel.resetFolderId() },
                    modifier = Modifier.height(35.dp)
                        .width(145.dp),
                    colors = ButtonColors(
                        containerColor = Color.White,
                        contentColor = tertiary,
                        disabledContentColor = Color.Gray,
                        disabledContainerColor = Color.Gray,
                    )
                )
                {
                    Text(
                        text = "Reset Folder",
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        fontFamily = Font.RussoOne
                    )
                }
                Button(
                    onClick = { screenModel.initBackup() },
                    modifier = Modifier.height(35.dp)
                        .width(125.dp),
                    colors = ButtonColors(
                        containerColor = Color.White,
                        contentColor = tertiary,
                        disabledContentColor = Color.Gray,
                        disabledContainerColor = Color.Gray,
                    )
                )
                {
                    Text(
                        text = "Backup",
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        fontFamily = Font.RussoOne
                    )
                }
                Column(
                    modifier = Modifier.height(45.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    SecurityContext.getGooglePersonDisplayName?.let {
                        Text(
                            text = it,
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Normal,
                            fontSize = 10.sp,
                            fontFamily = Font.RobotoThin,
                            color = Color.White,
                        )
                    }

                    val (text, color) = when (googleAppState) {
                        is GoogleAppState.Authenticated -> "Authenticated" to googleAppState.color
                        is GoogleAppState.AuthenticationError -> googleAppState.message to googleAppState.color
                        is GoogleAppState.NotAuthenticated -> "Not Authenticated" to googleAppState.color
                        is GoogleAppState.Authenticating -> "Authenticating..." to googleAppState.color
                        else -> "Unknown State" to Color.Gray
                    }

                    Text(
                        text = text,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        fontFamily = Font.RobotoThin,
                        color = color
                    )

                }

            }

        }
    }
}