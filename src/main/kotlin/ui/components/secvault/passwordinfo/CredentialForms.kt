package ui.components.secvault.passwordinfo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import ui.components.UnderLineTextFiled

@Composable
fun PasswordCredentialForm() {
    var userName by remember { mutableStateOf("") }

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
                field = userName,
                onFieldChange = { userName = it },
                label = "Password",
                modifier = Modifier.fillMaxWidth(),
                isPassword = true
            )
        }

        Row(modifier = Modifier.weight(1f)) {
            UnderLineTextFiled(
                field = userName,
                onFieldChange = { userName = it },
                label = "Email",
                modifier = Modifier.fillMaxWidth()
            )
        }

        Row(modifier = Modifier.weight(1f)) {
            UnderLineTextFiled(
                field = userName,
                onFieldChange = { userName = it },
                label = "Website",
                modifier = Modifier.fillMaxWidth()
            )
        }

    }
}

@Composable
fun CreditCardCredentialForm() {
    var userName by remember { mutableStateOf("") }

    Column() {
        Row(modifier = Modifier.weight(1f)) {
            UnderLineTextFiled(
                field = userName,
                onFieldChange = { userName = it },
                label = "Bank Name",
                modifier = Modifier.fillMaxWidth()
            )
        }

        Row(modifier = Modifier.weight(1f)) {
            UnderLineTextFiled(
                field = userName,
                onFieldChange = { userName = it },
                label = "Card Owner",
                modifier = Modifier.fillMaxWidth()
            )
        }

        Row(modifier = Modifier.weight(1f)) {
            UnderLineTextFiled(
                field = userName,
                onFieldChange = { userName = it },
                label = "Card Number",
                modifier = Modifier.fillMaxWidth()
            )
        }

        Row(modifier = Modifier.weight(1f)) {
            UnderLineTextFiled(
                field = userName,
                onFieldChange = { userName = it },
                label = "CVC",
                modifier = Modifier.fillMaxWidth()
            )
        }

        Row(modifier = Modifier.weight(1f)) {
            UnderLineTextFiled(
                field = userName,
                onFieldChange = { userName = it },
                label = "Pin",
                modifier = Modifier.fillMaxWidth()
            )
        }

        Row(modifier = Modifier.weight(1f)) {
            UnderLineTextFiled(
                field = userName,
                onFieldChange = { userName = it },
                label = "Expiry Date",
                modifier = Modifier.fillMaxWidth()
            )
        }

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