package ui.components.secvault.passwordinfo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import ui.components.UnderLineTextFiled
import viewmodel.SecVaultScreenModel

@Composable
fun PasswordCredentialForm(screenModel: SecVaultScreenModel) {
    val selectedCredential by screenModel.selectedCredential.collectAsState()

    var userName by remember(selectedCredential) {
        mutableStateOf(selectedCredential.password?.username ?: "")
    }

    var password by remember(selectedCredential) {
        mutableStateOf(
            selectedCredential.password?.password?.let {
                screenModel.decryptPassword(it)
            } ?: ""
        )
    }

    var email by remember(selectedCredential) {
        mutableStateOf(selectedCredential.password?.email ?: "")
    }

    var website by remember(selectedCredential) {
        mutableStateOf(selectedCredential.password?.website ?: "")
    }

    Column() {
        Row(modifier = Modifier.weight(1f)) {
            UnderLineTextFiled(
                field = userName,
                onFieldChange = { userName = it },
                label = "Username",
                modifier = Modifier.fillMaxWidth()
            )
        }

        Row(modifier = Modifier.weight(1f)) {
            UnderLineTextFiled(
                field = password,
                onFieldChange = { password = it },
                label = "Password",
                modifier = Modifier.fillMaxWidth(),
                isPassword = true
            )
        }

        Row(modifier = Modifier.weight(1f)) {
            UnderLineTextFiled(
                field = email,
                onFieldChange = { email = it },
                label = "Email",
                modifier = Modifier.fillMaxWidth()
            )
        }

        Row(modifier = Modifier.weight(1f)) {
            UnderLineTextFiled(
                field = website,
                onFieldChange = { website = it },
                label = "Website",
                modifier = Modifier.fillMaxWidth()
            )
        }

    }
}

@Composable
fun CreditCardCredentialForm(screenModel: SecVaultScreenModel) {
    val selectedCredential by screenModel.selectedCredential.collectAsState()

    var bankName by remember(selectedCredential) {
        mutableStateOf(selectedCredential.creditCard?.name ?: "")
    }

    var cardOwner by remember(selectedCredential) {
        mutableStateOf(selectedCredential.creditCard?.owner?.userName ?: "")
    }

    var cardNumber by remember(selectedCredential) {
        mutableStateOf(selectedCredential.creditCard?.number ?: "")
    }

    var cvc by remember(selectedCredential) {
        mutableStateOf(selectedCredential.creditCard?.cvc ?: "")
    }

    var pin by remember(selectedCredential) {
        mutableStateOf(selectedCredential.creditCard?.pin ?: "")
    }

    var expiryDate by remember(selectedCredential) {
        mutableStateOf(selectedCredential.creditCard?.expiryDate ?: "")
    }

    var notes by remember(selectedCredential) {
        mutableStateOf(selectedCredential.creditCard?.notes ?: "")
    }

    Column() {
        Row(modifier = Modifier.weight(1f)) {
            UnderLineTextFiled(
                field = bankName,
                onFieldChange = { bankName = it },
                label = "Bank Name",
                modifier = Modifier.fillMaxWidth()
            )
        }

        Row(modifier = Modifier.weight(1f)) {
            UnderLineTextFiled(
                field = cardOwner,
                onFieldChange = { cardOwner = it },
                label = "Card Owner",
                modifier = Modifier.fillMaxWidth()
            )
        }

        Row(modifier = Modifier.weight(1f)) {
            UnderLineTextFiled(
                field = cardNumber,
                onFieldChange = { cardNumber = it },
                label = "Card Number",
                modifier = Modifier.fillMaxWidth()
            )
        }

        Row(modifier = Modifier.weight(1f)) {
            UnderLineTextFiled(
                field = cvc.toString(),
                onFieldChange = { cvc = it },
                label = "CVC",
                modifier = Modifier.fillMaxWidth()
            )
        }

        Row(modifier = Modifier.weight(1f)) {
            UnderLineTextFiled(
                field = pin.toString(),
                onFieldChange = { pin = it },
                label = "Pin",
                modifier = Modifier.fillMaxWidth()
            )
        }

        Row(modifier = Modifier.weight(1f)) {
            UnderLineTextFiled(
                field = expiryDate,
                onFieldChange = { expiryDate = it },
                label = "Expiry Date",
                modifier = Modifier.fillMaxWidth()
            )
        }

        Row(modifier = Modifier.weight(1f)) {
            UnderLineTextFiled(
                field = notes,
                onFieldChange = { notes = it },
                label = "Notes",
                modifier = Modifier.fillMaxWidth(),
                singleLine = false
            )
        }
    }
}

@Composable
fun NotesCredentialForm() {
    var userName by remember { mutableStateOf("") }

    Column() {
        Row(modifier = Modifier.weight(1f)) {
            UnderLineTextFiled(
                field = userName,
                onFieldChange = { userName = it },
                label = "Notes",
                modifier = Modifier.fillMaxWidth(),
                singleLine = false
            )
        }
    }
}