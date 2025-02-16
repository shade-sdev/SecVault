package viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import core.external.google.GoogleAppState
import core.external.google.GoogleAuthManager
import core.models.Result
import core.models.UiState
import core.models.dto.GoogleDriveConfigDto
import core.security.SecurityContext
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
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ScreenModel {

    private val _settingState = MutableStateFlow<UiState<Boolean>>(UiState.Loading)
    val settingState: StateFlow<UiState<Boolean>> = _settingState.asStateFlow()

    private val _fileDialogState = MutableStateFlow(false)
    val fileDialogState: StateFlow<Boolean> = _fileDialogState.asStateFlow()

    private val _googleAppState = MutableStateFlow<GoogleAppState?>(GoogleAppState.getInitialAppState())
    val googleAppState: StateFlow<GoogleAppState?> = _googleAppState.asStateFlow()

    fun openDialog() {
        _fileDialogState.value = true
    }

    fun closeDialog() {
        _fileDialogState.value = false
    }

    fun showSelectFileDialog(): File? {
        val dialog = FileDialog(null as Frame?, "Select a JSON File", FileDialog.LOAD).apply {
            file = "*.json"
            isVisible = true
        }
        dialog.directory?.let { dir ->
            dialog.file?.let { fileName ->
                val selectedFile = File(dir, fileName)
                if (selectedFile.exists() && selectedFile.extension.equals("json", ignoreCase = true)) {
                    return selectedFile
                }
            }
        }

        return null
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

    fun authenticateGoogleDrive() {
        _googleAppState.value = GoogleAppState.Authenticating()
        screenModelScope.launch(dispatcher) {
            _settingState.value = UiState.Loading
            when (val result =
                googleDriveConfigRepository.findByUserId(SecurityContext.getUserId!!)
            ) {
                is Result.Success -> {
                    result.data.let { googleDriveConfig ->
                        val bytes = googleDriveConfig.configFile.bytes
                        googleAuthManager.authenticate(bytes.inputStream())
                    }
                    _googleAppState.value = GoogleAppState.Authenticated()
                    _settingState.value = UiState.Success(true, "Successfully authenticated")
                }

                is Result.Error -> {
                    _googleAppState.value = GoogleAppState.AuthenticationError(result.message)
                    _settingState.value = UiState.Error(result.message)
                }
            }
        }
    }

    fun clearError() {
        _settingState.value = UiState.Idle
    }

}