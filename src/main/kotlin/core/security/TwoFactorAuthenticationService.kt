package core.security

import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.toComposeImageBitmap
import com.atlassian.onetime.core.TOTP
import com.atlassian.onetime.model.EmailAddress
import com.atlassian.onetime.model.Issuer
import com.atlassian.onetime.model.TOTPSecret
import com.atlassian.onetime.service.DefaultTOTPService
import com.atlassian.onetime.service.RandomSecretProvider
import com.google.zxing.BarcodeFormat
import com.google.zxing.client.j2se.MatrixToImageConfig
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import core.Config
import core.configs.JwtConfig
import core.models.Result
import java.awt.Color
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

class TwoFactorAuthenticationService(config: Config) {

    private val jwtConfig: JwtConfig = config.jwt

    fun generateSecretKey(): TOTPSecret {
        val randomSecretProvider = RandomSecretProvider()
        return randomSecretProvider.generateSecret()
    }

    fun verifySecret(secretKey: String, code: String): Result<Boolean> {
        val totpCode = TOTP(code)
        val totpSecret = TOTPSecret.fromBase32EncodedString(secretKey)

        return DefaultTOTPService().verify(totpCode, totpSecret)
            .takeIf { it.isSuccess() }
            ?.let { Result.Success(true) }
            ?: Result.Error("Invalid TOTP secret")
    }

    fun generateQRCodeImage(secretKey: String, email: String): Result<BitmapPainter> {
        val topSecret = TOTPSecret.fromBase32EncodedString(secretKey)
        val otpAuthUri = DefaultTOTPService().generateTOTPUrl(
            topSecret,
            EmailAddress(email),
            Issuer(jwtConfig.issuer),
        ).toString()

        val width = 400
        val height = 400

        return runCatching {
            val writer = QRCodeWriter()
            val bitMatrix = writer.encode(otpAuthUri, BarcodeFormat.QR_CODE, width, height)
            val config = MatrixToImageConfig(Color.BLACK.rgb, Color.WHITE.rgb)

            val bufferedImage: BufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix, config)

            val byteArrayOutputStream = ByteArrayOutputStream()
            ImageIO.write(bufferedImage, "png", byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()

            val imageBitmap = org.jetbrains.skia.Image.makeFromEncoded(byteArray).toComposeImageBitmap()

            BitmapPainter(imageBitmap)
        }.fold(
            onSuccess = {
                Result.Success(it)
            },
            onFailure = { e ->
                Result.Error(e.localizedMessage ?: "Unknown error")
            }
        )

    }

}
