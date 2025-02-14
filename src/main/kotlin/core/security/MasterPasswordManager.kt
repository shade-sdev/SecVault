package core.security

import core.security.MasterPasswordManager.Constants.KEY_ALGORITHM
import core.security.MasterPasswordManager.Constants.SECRET_KEY_ALGORITHM
import core.security.MasterPasswordManager.Constants.TRANSFORMATION
import java.nio.charset.StandardCharsets
import java.security.SecureRandom
import java.security.spec.KeySpec
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

/**
 * Object responsible for managing master passwords, including encryption and decryption.
 */
object MasterPasswordManager {

    object Constants {
        const val TRANSFORMATION = "AES/GCM/NoPadding"
        const val KEY_ALGORITHM = "PBKDF2WithHmacSHA256"
        const val SECRET_KEY_ALGORITHM = "AES"
    }

    /**
     * Converts a plain string to a secure character array.
     *
     * @param value The plain string to convert.
     * @return The secure character array.
     */
    fun convertToSecureString(value: String): CharArray = value.toCharArray()

    /**
     * Converts a secure character array back to a plain string.
     *
     * @param secureString The secure character array to convert.
     * @return The plain string.
     */
    fun convertToUnsecureString(secureString: CharArray): String = String(secureString)

    /**
     * Encrypts a plain text string using the provided key.
     *
     * @param plainText The plain text string to encrypt.
     * @param key The encryption key.
     * @return The encrypted string, encoded in Base64.
     */
    fun encryptString(plainText: String, key: ByteArray): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val iv = ByteArray(12).apply { SecureRandom().nextBytes(this) }
        cipher.init(Cipher.ENCRYPT_MODE, SecretKeySpec(key, SECRET_KEY_ALGORITHM), GCMParameterSpec(128, iv))
        val encrypted = cipher.doFinal(plainText.toByteArray(StandardCharsets.UTF_8))
        return Base64.getEncoder().encodeToString(iv + encrypted)
    }

    /**
     * Decrypts an encrypted string using the provided key.
     *
     * @param encryptedText The encrypted string, encoded in Base64.
     * @param key The decryption key.
     * @return The decrypted plain text string.
     */
    fun decryptString(encryptedText: String, key: ByteArray): String {
        val combined = Base64.getDecoder().decode(encryptedText)
        val iv = combined.copyOf(12)
        val encrypted = combined.copyOfRange(12, combined.size)
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.DECRYPT_MODE, SecretKeySpec(key, SECRET_KEY_ALGORITHM), GCMParameterSpec(128, iv))
        return String(cipher.doFinal(encrypted), StandardCharsets.UTF_8)
    }

    /**
     * Generates a key from a secret key string using PBKDF2 with HMAC SHA-256.
     *
     * @param secretKey The secret key string.
     * @return The generated key as a byte array.
     */
    fun getKey(secretKey: String): ByteArray {
        val salt = secretKey.toByteArray(StandardCharsets.UTF_8)
        val spec: KeySpec = PBEKeySpec(secretKey.toCharArray(), salt, 65536, 256)
        return SecretKeyFactory.getInstance(KEY_ALGORITHM).generateSecret(spec).encoded
    }

}