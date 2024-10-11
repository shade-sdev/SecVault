package ui.components.forms

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ui.components.FormTextField
import ui.components.PasswordTextField
import ui.theme.Font
import ui.theme.primary
import ui.theme.secondary

@Preview
@Composable
fun PasswordForm() {

    Column(
        modifier = Modifier.fillMaxSize()
                .background(secondary)
    )
    {

        Row(
            modifier = Modifier.weight(1f)
                    .fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "Create a new password",
                color = Color.White,
                fontFamily = Font.RobotoRegular,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
            )
        }

        Row(
            modifier = Modifier.weight(7.5f)
                    .fillMaxSize()
        ) {

            Column(
                modifier = Modifier.weight(1f)
                        .fillMaxSize()
                        .background(primary)
                        .padding(PaddingValues(end = 20.dp)),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterVertically)
            ) {
                FormTextField(
                    value = "fe",
                    onValueChange = {},
                    label = "Username",
                    icon = Icons.Filled.AccountCircle,
                    modifier = Modifier.height(45.dp).width(400.dp)
                )
                FormTextField(
                    value = "fe",
                    onValueChange = {},
                    label = "Email",
                    icon = Icons.Filled.Email,
                    modifier = Modifier.height(45.dp).width(400.dp)
                )
                PasswordTextField(
                    value = "fe",
                    onValueChange = {},
                    label = "Password",
                    modifier = Modifier.height(45.dp).width(400.dp)
                )
            }

            Column(
                modifier = Modifier.weight(1f)
                        .fillMaxSize()
                        .background(primary)
                        .padding(PaddingValues(start = 20.dp)),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterVertically)
            ) {
                FormTextField(
                    value = "fe",
                    onValueChange = {},
                    label = "Name",
                    icon = Icons.Filled.Web,
                    modifier = Modifier.height(45.dp).width(400.dp)
                )
                FormTextField(
                    value = "fe",
                    onValueChange = {},
                    label = "Website Url",
                    icon = Icons.Filled.Link,
                    modifier = Modifier.height(45.dp).width(400.dp)
                )
                FormTextField(
                    value = "fe",
                    onValueChange = {},
                    label = "Website Icon Url",
                    icon = Icons.Filled.Info,
                    modifier = Modifier.height(45.dp).width(400.dp)
                )
            }
        }

        Row(
            modifier = Modifier.weight(1.5f)
                    .fillMaxSize()
                    .background(primary),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterHorizontally)
        ) {
            Button(
                onClick = {},
                modifier = Modifier.width(175.dp),
                enabled = true,
                colors = ButtonColors(
                    containerColor = secondary,
                    contentColor = Color.White,
                    disabledContentColor = secondary,
                    disabledContainerColor = secondary,
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
                onClick = {},
                modifier = Modifier.width(175.dp),
                colors = ButtonColors(
                    containerColor = secondary,
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

    }

}