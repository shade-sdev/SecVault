package ui.screens

import androidx.compose.animation.*
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import kotlinx.coroutines.delay
import ui.components.LoadingScreen
import ui.theme.Font
import ui.theme.secondary
import ui.theme.tertiary

class LoginSplashScreen() : Screen {

    @Preview
    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current
        var isLoading by remember { mutableStateOf(true) }
        var isVisible by remember { mutableStateOf(false) }

        LaunchedEffect(Unit) {
            delay(600)
            isLoading = false
            isVisible = true
            delay(1500)
            navigator?.pop()
        }

        Row(
            modifier = Modifier.fillMaxSize()
                .background(secondary),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        )
        {

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            )
            {
                if (isLoading) {
                    LoadingScreen(
                        text = "Loading...",
                        backgroundColor = tertiary,
                        indicatorColor = Color.White,
                        textColor = Color.White
                    )
                }
                AnimatedVisibility(
                    visible = isVisible,
                    enter = slideInVertically() + fadeIn(), exit = fadeOut()
                )
                {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    )
                    {
                        Text(
                            text = "Welcome Back",
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Normal,
                            color = Color.White,
                            fontSize = 55.sp,
                            fontFamily = Font.RussoOne
                        )
                    }
                }

                AnimatedVisibility(
                    visible = isVisible,
                    enter = slideInHorizontally() + fadeIn()
                )
                {
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    )
                    {
                        Icon(
                            imageVector = Icons.Filled.AccountCircle,
                            contentDescription = "Setting button",
                            modifier = Modifier.size(80.dp),
                            tint = Color.White
                        )
                        Text(
                            text = "Login",
                            fontStyle = FontStyle.Normal,
                            fontWeight = FontWeight.Normal,
                            color = Color.White,
                            fontSize = 40.sp,
                            fontFamily = Font.RussoOne
                        )
                    }
                }
            }
        }

    }
}