package ui.components.forms.passwordmgnt

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import ui.theme.primary
import ui.theme.secondary
import ui.theme.tertiary

class NoteForm : Screen {

    @Composable
    override fun Content() {

        val navigator = LocalNavigator.current

        Column(
            modifier = Modifier.fillMaxSize()
                .background(secondary)
        )
        {

            Row(
                modifier = Modifier.weight(1f)
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Header(
                    creditCardButtonShown = true,
                    notesButtonShown = false
                )
            }

            Row(
                modifier = Modifier.weight(7.5f)
                    .background(primary)
                    .fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {


            }

            Row(
                modifier = Modifier.weight(1.5f)
                    .fillMaxSize()
                    .background(tertiary),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterHorizontally)
            ) {
                Footer(
                    { },
                    { navigator?.pop() },
                    true
                )
            }

        }
    }

}