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

    /**
     * Converts a string to a SecureString equivalent in Kotlin (emulated).
     * Kotlin doesn't have a `SecureString`, so use char arrays for sensitive data.
     */
    fun convertToSecureString(value: String): CharArray {
        return value.toCharArray()
    }

    /**
     * Converts a char array back to a regular string.
     */
    fun convertToUnsecureString(secureString: CharArray): String {
        return String(secureString)
    }

    /**
     * Encrypts a string using AES (Advanced Encryption Standard) algorithm.
     */
    fun encryptString(plainText: String, key: ByteArray): String {
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val iv = ByteArray(cipher.blockSize)
        SecureRandom().nextBytes(iv)
        val ivSpec = IvParameterSpec(iv)
        cipher.init(Cipher.ENCRYPT_MODE, SecretKeySpec(key, "AES"), ivSpec)

        val encrypted = cipher.doFinal(plainText.toByteArray(StandardCharsets.UTF_8))
        val combined = iv + encrypted

        return Base64.getEncoder().encodeToString(combined)
    }

    /**
     * Decrypts a string that was encrypted using AES (Advanced Encryption Standard) algorithm.
     */
    fun decryptString(encryptedText: String, key: ByteArray): String {
        val combined = Base64.getDecoder().decode(encryptedText)
        val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
        val iv = combined.copyOf(cipher.blockSize)
        val encrypted = combined.copyOfRange(cipher.blockSize, combined.size)
        val ivSpec = IvParameterSpec(iv)

        cipher.init(Cipher.DECRYPT_MODE, SecretKeySpec(key, "AES"), ivSpec)
        val decrypted = cipher.doFinal(encrypted)

        return String(decrypted, StandardCharsets.UTF_8)
    }

    /**
     * Derives a cryptographic key from a password and a secret key using PBKDF2 algorithm.
     */
    fun getKey(secretKey: String): ByteArray {
        val salt = secretKey.toByteArray(StandardCharsets.UTF_8)
        val spec: KeySpec = PBEKeySpec(secretKey.toCharArray(), salt, 10000, 256)
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        return factory.generateSecret(spec).encoded
    }

}
