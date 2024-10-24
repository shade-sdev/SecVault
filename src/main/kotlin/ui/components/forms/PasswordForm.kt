package ui.components.forms

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.form.validation.FormValidator
import core.models.dto.PasswordDto
import ui.components.FormTextField
import ui.components.MultiSelectDropdown
import ui.components.PasswordTextField
import ui.theme.Font
import ui.theme.primary
import ui.theme.secondary
import ui.theme.tertiary
import ui.validators.PasswordFormFieldName
import ui.validators.toPasswordDto
import viewmodel.PasswordMgntScreenModel

@Preview
@Composable
fun PasswordForm(
    formValidator: FormValidator,
    screenModel: PasswordMgntScreenModel,
    isFormValid: Boolean,
    onSaveClick: (PasswordDto) -> Unit,
    onCancelClick: () -> Unit
) {

    val userName = formValidator.getField(PasswordFormFieldName.USERNAME)
    val email = formValidator.getField(PasswordFormFieldName.EMAIL)
    val password = formValidator.getField(PasswordFormFieldName.PASSWORD)

    val name = formValidator.getField(PasswordFormFieldName.NAME)
    val webSiteUrl = formValidator.getField(PasswordFormFieldName.WEBSITE_URL)
    val icon = formValidator.getField(PasswordFormFieldName.WEBSITE_ICON_URL)

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

        Row(
            modifier = Modifier.weight(7.5f)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f)
                    .fillMaxSize()
                    .background(primary)
                    .padding(PaddingValues(end = 20.dp)),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically)
            ) {
                Column(modifier = Modifier.height(80.dp)) {
                    FormTextField(
                        value = userName?.value?.value ?: "",
                        onValueChange = { newValue ->
                            userName?.value?.value = newValue
                            formValidator.validateField(PasswordFormFieldName.USERNAME)
                            formValidator.validateField(PasswordFormFieldName.EMAIL)
                        },
                        label = "Username",
                        icon = Icons.Filled.AccountCircle,
                        modifier = Modifier.height(45.dp).width(400.dp)
                    )
                    userName?.error?.value?.let {
                        Text(
                            text = it,
                            color = Color.Red,
                            fontFamily = Font.RobotoRegular,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
                Column(modifier = Modifier.height(80.dp)) {
                    FormTextField(
                        value = email?.value?.value ?: "",
                        onValueChange = { newValue ->
                            email?.value?.value = newValue
                            formValidator.validateField(PasswordFormFieldName.EMAIL)
                            formValidator.validateField(PasswordFormFieldName.USERNAME)
                        },
                        label = "Email",
                        icon = Icons.Filled.Email,
                        modifier = Modifier.height(45.dp).width(400.dp)
                    )
                    email?.error?.value?.let {
                        Text(
                            text = it,
                            color = Color.Red,
                            fontFamily = Font.RobotoRegular,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
                Column(modifier = Modifier.height(80.dp)) {
                    PasswordTextField(
                        value = password?.value?.value ?: "",
                        onValueChange = { newValue ->
                            password?.value?.value = newValue
                            formValidator.validateField(PasswordFormFieldName.PASSWORD)
                        },
                        label = "Password",
                        modifier = Modifier.height(45.dp).width(400.dp)
                    )
                    password?.error?.value?.let {
                        Text(
                            text = it,
                            color = Color.Red,
                            fontFamily = Font.RobotoRegular,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
            }

            Column(
                modifier = Modifier.weight(1f)
                    .fillMaxSize()
                    .background(primary)
                    .padding(PaddingValues(start = 20.dp)),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically)
            ) {
                Column(modifier = Modifier.height(80.dp)) {
                    FormTextField(
                        value = name?.value?.value ?: "",
                        onValueChange = { newValue ->
                            name?.value?.value = newValue
                            formValidator.validateField(PasswordFormFieldName.NAME)
                        },
                        label = "Name",
                        icon = Icons.Filled.Web,
                        modifier = Modifier.height(45.dp).width(400.dp)
                    )
                    name?.error?.value?.let {
                        Text(
                            text = it,
                            color = Color.Red,
                            fontFamily = Font.RobotoRegular,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
                Column(modifier = Modifier.height(80.dp)) {
                    FormTextField(
                        value = webSiteUrl?.value?.value ?: "",
                        onValueChange = { newValue ->
                            webSiteUrl?.value?.value = newValue
                            formValidator.validateField(PasswordFormFieldName.WEBSITE_URL)
                        },
                        label = "Website Url",
                        icon = Icons.Filled.Link,
                        modifier = Modifier.height(45.dp).width(400.dp)
                    )
                    webSiteUrl?.error?.value?.let {
                        Text(
                            text = it,
                            color = Color.Red,
                            fontFamily = Font.RobotoRegular,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }
                Column(modifier = Modifier.height(80.dp)) {
                    FormTextField(
                        value = icon?.value?.value ?: "",
                        onValueChange = { newValue ->
                            icon?.value?.value = newValue
                            formValidator.validateField(PasswordFormFieldName.WEBSITE_ICON_URL)
                        },
                        label = "Website Icon Url",
                        icon = Icons.Filled.Info,
                        modifier = Modifier.height(45.dp).width(400.dp)
                    )
                    icon?.error?.value?.let {
                        Text(
                            text = it,
                            color = Color.Red,
                            fontFamily = Font.RobotoRegular,
                            fontSize = 10.sp,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                }

                Column(modifier = Modifier.height(80.dp)) {
                    val items = listOf("Apple", "Banana", "Cherry", "Date", "Elderberry")
                    var selectedItems by remember { mutableStateOf(listOf<String>("Apple")) }

                    MultiSelectDropdown(
                        items = items,
                        selectedItems = selectedItems,
                        onItemSelect = { selectedItems = selectedItems + it },
                        onItemDeselect = { selectedItems = selectedItems - it },
                        itemToString = { it },
                        modifier = Modifier.height(55.dp).width(400.dp)
                    )
                }
            }
        }

        Row(
            modifier = Modifier.weight(1.5f)
                .fillMaxSize()
                .background(tertiary),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(30.dp, Alignment.CenterHorizontally)
        ) {
            Button(
                onClick = { onSaveClick(toPasswordDto(formValidator, screenModel.getAuthenticatedUser())) },
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

    }

}