package core.models

/**
 * A sealed class representing a result, which can be either a success or an error.
 */
sealed class Result<out T> {
    /**
     * Represents a successful result containing data.
     *
     * @param T The type of the data.
     * @property data The data of the successful result.
     */
    data class Success<out T>(val data: T) : Result<T>()

    /**
     * Represents an error result containing a message.
     *
     * @property message The error message.
     */
    data class Error(val message: String) : Result<Nothing>()
}