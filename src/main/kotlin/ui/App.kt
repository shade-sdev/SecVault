package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ui.theme.PasswordColors

@Composable
fun App() {

    var text by remember { mutableStateOf("Hello, World!") }

    MaterialTheme(colorScheme = PasswordColors) {
        Column(
            modifier = Modifier.fillMaxSize()
                .background(color = MaterialTheme.colorScheme.secondaryContainer),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        )
        {
            Button(onClick = {
                text = "Hello, Desktop!"
            })
            {
                Text(text)
            }
        }
    }

}