package ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun App() {

    var text by remember { mutableStateOf("Hello, World!") }

    MaterialTheme {
        Column(
            modifier = Modifier.fillMaxSize()
                .background(color = MaterialTheme.colorScheme.background)
        )
        {
            Button(onClick = {
                text = "Hello, Desktop!"
            }) {
                Text(text)
            }
        }
    }

}