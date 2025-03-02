package core.models

sealed class SecVaultEvent {
    data object LoadCredentials : SecVaultEvent()
}