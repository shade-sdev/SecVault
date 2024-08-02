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
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.inject
import org.slf4j.Logger
import ui.theme.PasswordColors
import java.lang.invoke.MethodHandles

@Composable
fun App() {

    val log: Logger by inject(Logger::class.java) {
        parametersOf(MethodHandles.lookup().lookupClass())
    }

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
                log.info("waaa")
                text = "Welcome to Jetpack Compose"
            })
            {
                Text(text)
            }
        }
    }

}