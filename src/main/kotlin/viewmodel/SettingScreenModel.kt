package viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import core.external.google.AuthState
import core.external.google.GoogleAppState
import core.external.google.GoogleAuthManager
import core.job.BackupJob
import core.models.Result
import core.models.UiState
import core.models.dto.GoogleDriveConfigDto
import core.models.enumeration.FileExtensions
import core.security.SecurityContext
import core.service.ExcelImportService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import repository.google.GoogleDriveConfigRepository
import java.awt.FileDialog
import java.awt.Frame
import java.io.File

class SettingScreenModel(
    private val googleDriveConfigRepository: GoogleDriveConfigRepository,
    private val googleAuthManager: GoogleAuthManager,
    private val importExcelImportService: ExcelImportService,
    private val backupJob: BackupJob,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ScreenModel {

    private val _settingState = MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val settingState: StateFlow<UiState<Boolean>> = _settingState.asStateFlow()

    private val _jsonFileDialogState = MutableStateFlow(false)
    val jsonFileDialogState: StateFlow<Boolean> = _jsonFileDialogState.asStateFlow()

    private val _importExcelFileDialogState = MutableStateFlow(false)
    val importExcelFileDialogState: StateFlow<Boolean> = _importExcelFileDialogState.asStateFlow()

    private val _saveExcelFileDialogState = MutableStateFlow(false)
    val saveExcelFileDialogState: StateFlow<Boolean> = _saveExcelFileDialogState.asStateFlow()

    private val _googleAppState = MutableStateFlow<GoogleAppState?>(GoogleAppState.getInitialAppState())
    val googleAppState: StateFlow<GoogleAppState?> = _googleAppState.asStateFlow()

    fun onJsonDialog() {
        _jsonFileDialogState.value = !_jsonFileDialogState.value
    }

    fun onImportExcelDialog() {
        _importExcelFileDialogState.value = !_importExcelFileDialogState.value
    }

    fun onExportExcelTemplateDialog() {
        _saveExcelFileDialogState.value = !_saveExcelFileDialogState.value
    }

    fun showSelectFileDialog(extension: FileExtensions): File? {
        val dialog = FileDialog(null as Frame?, "Select a JSON File", FileDialog.LOAD).apply {
            file = extension.filter
            isVisible = true
        }
        dialog.directory?.let { dir ->
            dialog.file?.let { fileName ->
                val selectedFile = File(dir, fileName)
                if (selectedFile.exists() && selectedFile.extension.equals(
                        extension.fileExtension,
                        ignoreCase = true
                    )
                ) {
                    return selectedFile
                }
            }
        }

        return null
    }

    fun saveTemplateDialog(): File {
        FileDialog(null as Frame?, "Save Template", FileDialog.SAVE).apply {
            file = "SecVault_Import_Template.xlsx"
            isVisible = true
        }.let { dialog ->
            dialog.directory?.let { dir ->
                dialog.file?.let { file ->
                    return File(dir, file)
                }
            }
        }
        return File("SecVault_Import_Template.xlsx")
    }

    fun saveConfigFile(file: ByteArray) {
        screenModelScope.launch(dispatcher) {
            _settingState.value = UiState.Loading
            when (val result =
                googleDriveConfigRepository.save(GoogleDriveConfigDto(SecurityContext.authenticatedUser!!, file))) {
                is Result.Success -> _settingState.value = UiState.Success(result.data, "File successfully saved")
                is Result.Error -> _settingState.value = UiState.Error(result.message)
            }
        }
    }

    fun importPasswords(file: ByteArray) {
        screenModelScope.launch(dispatcher) {
            _settingState.value = UiState.Loading
            when (val result =
                importExcelImportService.importExcel(file)) {
                is Result.Success -> _settingState.value = UiState.Success(true, "File successfully imported")
                is Result.Error -> _settingState.value = UiState.Error(result.message)
            }
        }
    }

    fun resetFolderId() {
        screenModelScope.launch(dispatcher) {
            _settingState.value = UiState.Loading
            when (val result =
                googleDriveConfigRepository.resetFolder(SecurityContext.getUserId!!)) {
                is Result.Success -> _settingState.value = UiState.Success(result.data, "Folder reset successful")
                is Result.Error -> _settingState.value = UiState.Error(result.message)
            }
        }
    }

    fun initBackup() {
        screenModelScope.launch(dispatcher) {
            _settingState.value = UiState.Loading
            when (val result = backupJob.start()) {
                is Result.Success -> _settingState.value = UiState.Success(result.data, "Backup successful")
                is Result.Error -> _settingState.value = UiState.Error(result.message)
            }
        }
    }

    fun authenticateGoogleDrive() {
        _googleAppState.value = GoogleAppState.Authenticating()
        screenModelScope.launch(dispatcher) {
            _settingState.value = UiState.Loading
            when (val result = googleAuthManager.authenticate()
            ) {
                is AuthState.Authorized -> {
                    _googleAppState.value = GoogleAppState.Authenticated()
                    _settingState.value = UiState.Success(true, "Successfully authenticated")
                }

                is AuthState.Failed -> {
                    _googleAppState.value = GoogleAppState.AuthenticationError(result.error)
                    _settingState.value = UiState.Error(result.error)
                }
            }
        }
    }

    fun clearError() {
        _settingState.value = UiState.Idle
    }

}