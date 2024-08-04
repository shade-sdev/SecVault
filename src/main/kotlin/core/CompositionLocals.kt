package core

import androidx.compose.runtime.staticCompositionLocalOf

val LocalAppState = staticCompositionLocalOf<AppState> { error("AppState not provided") }
