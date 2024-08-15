package ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import ui.components.secvault.passwordlayout.PasswordLayout
import ui.components.secvault.sidebar.SideBar
import ui.theme.PasswordColors
import viewmodel.SecVaultScreenModel

class SecVaultScreen : Screen {

    @Composable
    override fun Content() {

        val screenModel = koinScreenModel<SecVaultScreenModel>()

        Surface(
            modifier = Modifier.fillMaxSize()
        )
        {
            Row {

                Column(
                    modifier = Modifier.weight(3f)
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .background(PasswordColors.primary)
                )
                {
                    SideBar(screenModel)
                }

                Column(
                    modifier = Modifier.weight(4.5f)
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .background(PasswordColors.secondary)
                )
                {
                    PasswordLayout()
                }

                Column(
                    modifier = Modifier.weight(4.5f)
                            .fillMaxHeight()
                            .fillMaxWidth()
                            .background(PasswordColors.tertiary)
                )
                {
                    //PasswordInfo()
                }

            }
        }
    }
}