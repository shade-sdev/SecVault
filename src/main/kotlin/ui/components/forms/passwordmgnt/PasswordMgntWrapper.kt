package ui.components.forms.passwordmgnt

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import ui.components.OutlineTextButton
import ui.theme.Font
import ui.theme.primary
import ui.theme.secondary

@Composable
fun Header(
    creditCardButtonShown: Boolean,
    notesButtonShown: Boolean
) {
    val navigator = LocalNavigator.current

    Row {
        Spacer(modifier = Modifier.width(25.dp))
        Text(
            text = "Create a new password",
            color = Color.White,
            fontFamily = Font.RussoOne,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
        )
    }

    Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {

        if (creditCardButtonShown) {
            OutlineTextButton(
                modifier = Modifier.size(height = 28.dp, width = 100.dp),
                cornerSize = 4.dp,
                fontSize = 12.sp,
                text = "Credit Card",
                onClick = { navigator?.push(CreditCardForm()) }
            )
        }

        if (notesButtonShown) {
            OutlineTextButton(
                modifier = Modifier.size(height = 28.dp, width = 100.dp),
                cornerSize = 4.dp,
                fontSize = 12.sp,
                text = "Notes",
                onClick = { navigator?.push(NoteForm()) }
            )
        }
        Spacer(modifier = Modifier.width(25.dp))
    }
}

@Composable
fun Footer(
    onSaveClick: () -> Unit,
    onCancelClick: () -> Unit,
    isFormValid: Boolean
) {
    Button(
        onClick = { onSaveClick() },
        modifier = Modifier.width(175.dp),
        enabled = isFormValid,
        colors = ButtonColors(
            containerColor = primary,
            contentColor = Color.White,
            disabledContentColor = Color.Gray,
            disabledContainerColor = primary,
        )
    )
    {
        Text(
            color = Color.White,
            text = "Save",
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            fontFamily = Font.RussoOne
        )
    }
    Button(
        onClick = onCancelClick,
        modifier = Modifier.width(175.dp),
        colors = ButtonColors(
            containerColor = primary,
            contentColor = Color.White,
            disabledContentColor = secondary,
            disabledContainerColor = secondary
        )
    )
    {
        Text(
            text = "Cancel",
            fontStyle = FontStyle.Normal,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            fontFamily = Font.RussoOne
        )
    }
}