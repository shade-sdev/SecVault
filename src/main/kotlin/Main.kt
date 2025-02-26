import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import di.appModule
import di.repositoryModule
import di.viewModelModule
import org.koin.core.context.GlobalContext.startKoin
import ui.App

/**
 * Main entry point of the application.
 * Initializes Koin for dependency injection and sets up the main app window.
 */
fun main() = application {

    startKoin {
        modules(appModule, repositoryModule, viewModelModule)
    }

    Window(
        onCloseRequest = ::exitApplication,
        resizable = true,
        state = WindowState(size = DpSize(1150.dp, 700.dp), position = WindowPosition.Aligned(Alignment.Center)),
        undecorated = true,
        transparent = true,
        icon = painterResource("assets/icon.png")
    )
    {
        WindowDraggableArea(
            modifier = Modifier.clip(RoundedCornerShape(10.dp))
                    .fillMaxSize()
        )
        {
            App()
        }
    }
}
