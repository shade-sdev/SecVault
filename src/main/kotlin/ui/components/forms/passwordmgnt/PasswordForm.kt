package ui.components.forms.passwordmgnt

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import core.form.validation.FormValidator
import core.models.FormType
import core.models.PasswordCategory
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
    onCancelClick: () -> Unit,
    formType: FormType
) {

    val userName = formValidator.getField(PasswordFormFieldName.USERNAME)
    val email = formValidator.getField(PasswordFormFieldName.EMAIL)
    val password = formValidator.getField(PasswordFormFieldName.PASSWORD)

    val name = formValidator.getField(PasswordFormFieldName.NAME)
    val webSiteUrl = formValidator.getField(PasswordFormFieldName.WEBSITE_URL)
    val icon = formValidator.getField(PasswordFormFieldName.WEBSITE_ICON_URL)
    val passwordCategory = formValidator.getField(PasswordFormFieldName.PASSWORD_CATEGORY)

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
                creditCardButtonShown = (formType == FormType.CREATION),
                notesButtonShown = (formType == FormType.CREATION),
                title = if (formType == FormType.CREATION) "Create Password" else "Update Password",
            )
        }

        Row(
            modifier = Modifier.weight(7.5f)
                    .background(primary)
                    .fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.background(primary)
                        .padding(PaddingValues(end = 20.dp)),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically)
            ) {

                Row(horizontalArrangement = Arrangement.spacedBy(25.dp)) {
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
                }

                Row(horizontalArrangement = Arrangement.spacedBy(25.dp)) {
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
                }

                Row(horizontalArrangement = Arrangement.spacedBy(25.dp)) {
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
                }

                Row(horizontalArrangement = Arrangement.spacedBy(25.dp)) {
                    Column(modifier = Modifier.height(80.dp)) {
                        val items = PasswordCategory.entries.toSet()

                        val savedCategories =
                            passwordCategory?.value?.value?.split(",")?.map { PasswordCategory.valueOf(it) }?.sorted()
                                    ?.toSet() ?: emptySet()

                        var selectedItems by remember { mutableStateOf(savedCategories) }

                        LaunchedEffect(selectedItems) {
                            passwordCategory?.value?.value = selectedItems.joinToString(",") { it.name }
                        }

                        MultiSelectDropdown(
                            items = items,
                            selectedItems = selectedItems,
                            onItemSelect = { selectedItems = selectedItems + it },
                            onItemDeselect = { selectedItems = selectedItems - it },
                            itemToString = { it.value },
                            modifier = Modifier.height(55.dp).width(400.dp),
                            placeholder = "Select Password Genres",
                            backgroundColor = secondary,
                            foregroundColor = Color.White
                        )
                    }
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
            Footer(
                { onSaveClick(toPasswordDto(formValidator, screenModel.getAuthenticatedUser())) },
                onCancelClick,
                isFormValid
            )
        }

    }

}