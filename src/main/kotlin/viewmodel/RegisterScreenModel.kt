package viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.painter.BitmapPainter
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import core.models.Result
import core.models.UiState
import core.security.AuthenticationManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import repository.user.projection.UserSummary
import java.awt.FileDialog
import java.awt.Frame
import java.awt.Image
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

class RegisterScreenModel(
    private val authenticationManager: AuthenticationManager,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ScreenModel {

    private val _registerState = MutableStateFlow<UiState<UserSummary>>(UiState.Idle)
    val registerState: StateFlow<UiState<UserSummary>> = _registerState.asStateFlow()

    fun register(username: String, email: String, password: String) {
        screenModelScope.launch(dispatcher) {
            _registerState.value = UiState.Loading
            when (val result = authenticationManager.register(username, email, password)) {
                is Result.Success -> _registerState.value = UiState.Success(result.data)
                is Result.Error -> _registerState.value = UiState.Error(result.message)
            }
        }
    }

    fun openQRCode(user: UserSummary): Result<BitmapPainter> {
        return authenticationManager.openQRCode(user.secretKey, user.email)
    }

    fun showSaveDialog(): File {
        FileDialog(null as Frame?, "Save QR Code", FileDialog.SAVE).apply {
            file = "qrcode.png"
            isVisible = true
        }.let { dialog ->
            dialog.directory?.let { dir ->
                dialog.file?.let { file ->
                    return File(dir, file)
                }
            }
        }
        return File("qrcode.png")
    }

    fun saveQRCode(
        file: File,
        fileDialogState: MutableState<Boolean>,
        awtImage: Image
    ) {
        val bufferedImage = BufferedImage(
            awtImage.getWidth(null),
            awtImage.getHeight(null),
            BufferedImage.TYPE_INT_ARGB
        )

        val graphics = bufferedImage.createGraphics()
        graphics.drawImage(awtImage, 0, 0, null)
        graphics.dispose()

        ImageIO.write(bufferedImage, "png", file)
        fileDialogState.value = false
    }

    fun clearError() {
        _registerState.value = UiState.Idle
    }

}