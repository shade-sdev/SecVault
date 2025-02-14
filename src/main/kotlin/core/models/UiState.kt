package core.models

/**
 * Represents the state of the UI.
 *
 * @param T The type of data associated with the state.
 */
sealed class UiState<out T> {

    /**
     * Represents the idle state of the UI.
     */
    data object Idle : UiState<Nothing>()

    /**
     * Represents the loading state of the UI.
     */
    data object Loading : UiState<Nothing>()

    /**
     * Represents the success state of the UI with associated data.
     *
     * @param T The type of data.
     * @property data The data associated with the success state.
     */
    data class Success<T>(val data: T, val message: String? = "") : UiState<T>()

    /**
     * Represents the error state of the UI with an error message.
     *
     * @property message The error message.
     */
    data class Error(val message: String) : UiState<Nothing>()

}