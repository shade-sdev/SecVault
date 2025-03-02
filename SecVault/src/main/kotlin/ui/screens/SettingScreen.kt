package ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import com.dokar.sonner.ToastType
import com.dokar.sonner.Toaster
import com.dokar.sonner.rememberToasterState
import core.models.UiState
import core.models.enumeration.FileExtensions
import ui.components.setting.SettingScreenContent
import viewmodel.SettingScreenModel
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

class SettingScreen : Screen {

    @Composable
    override fun Content() {
        val screenModel = koinScreenModel<SettingScreenModel>()

        val settingState by screenModel.settingState.collectAsState()
        val jsonFileDialogState by screenModel.jsonFileDialogState.collectAsState()
        val excelFileDialogState by screenModel.importExcelFileDialogState.collectAsState()
        val saveExcelFileDialogState by screenModel.saveExcelFileDialogState.collectAsState()
        val toasterState = rememberToasterState()

        Toaster(
            state = toasterState,
            alignment = Alignment.TopEnd,
            darkTheme = true,
            showCloseButton = true,
            richColors = true
        )

        when {
            jsonFileDialogState -> {
                screenModel.showSelectFileDialog(FileExtensions.JSON)?.readBytes()?.let {
                    screenModel.saveConfigFile(it)
                    screenModel.onJsonDialog()
                }
            }
        }

        when {
            excelFileDialogState -> {
                screenModel.showSelectFileDialog(FileExtensions.XLSX)?.readBytes()?.let {
                    screenModel.importPasswords(it)
                    screenModel.onImportExcelDialog()
                }
            }
        }

        when {
            saveExcelFileDialogState -> {
                screenModel.saveTemplateDialog().let {
                    javaClass.classLoader.getResourceAsStream("assets/SecVault_Import_Template.xlsx")?.readBytes()
                        ?.let { excelFile -> it.writeBytes(excelFile) }
                    screenModel.onExportExcelTemplateDialog()
                }
            }
        }

        SettingScreenContent(screenModel)

        when (val state = settingState) {
            is UiState.Error -> {
                LaunchedEffect(toasterState) {
                    toasterState.show(
                        message = state.message,
                        type = ToastType.Error,
                        duration = Duration.INFINITE
                    )
                    screenModel.clearError()
                }
            }

            UiState.Idle -> {}
            UiState.Loading -> {}
            is UiState.Success<*> -> {
                LaunchedEffect(toasterState) {
                    toasterState.show(
                        message = state.message!!,
                        type = ToastType.Success,
                        duration = 5.seconds
                    )
                    screenModel.clearError()
                }
            }
        }

    }

}