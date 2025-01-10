package core.security

import java.nio.charset.StandardCharsets
import java.security.SecureRandom
import java.security.spec.KeySpec
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

object MasterPasswordManager {

    fun convertToSecureString(value: String): CharArray = value.toCharArray()

    fun convertToUnsecureString(secureString: CharArray): String = String(secureString)

    fun encryptString(plainText: String, key: ByteArray): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val iv = ByteArray(cipher.blockSize).apply { SecureRandom().nextBytes(this) }
        cipher.init(Cipher.ENCRYPT_MODE, SecretKeySpec(key, "AES"), IvParameterSpec(iv))
        val encrypted = cipher.doFinal(plainText.toByteArray(StandardCharsets.UTF_8))
        return Base64.getEncoder().encodeToString(iv + encrypted)
    }

    fun decryptString(encryptedText: String, key: ByteArray): String {
        val combined = Base64.getDecoder().decode(encryptedText)
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val iv = combined.copyOf(cipher.blockSize)
        val encrypted = combined.copyOfRange(cipher.blockSize, combined.size)
        cipher.init(Cipher.DECRYPT_MODE, SecretKeySpec(key, "AES"), IvParameterSpec(iv))
        return String(cipher.doFinal(encrypted), StandardCharsets.UTF_8)
    }

    fun getKey(secretKey: String): ByteArray {
        val salt = secretKey.toByteArray(StandardCharsets.UTF_8)
        val spec: KeySpec = PBEKeySpec(secretKey.toCharArray(), salt, 10000, 256)
        return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).encoded
    }

}