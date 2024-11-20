package viewmodel

sealed class SecVaultEvent {
    data object LoadCredentials : SecVaultEvent()
}