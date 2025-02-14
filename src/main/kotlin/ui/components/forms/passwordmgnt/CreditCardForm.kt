package ui.components.forms.passwordmgnt

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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import com.dokar.sonner.ToastType
import com.dokar.sonner.Toaster
import com.dokar.sonner.rememberToasterState
import core.form.validation.FormValidator
import core.models.FormType
import core.models.FormType.CREATION
import core.models.UiState
import core.security.SecurityContext
import kotlinx.coroutines.delay
import repository.creditcard.CreditCard
import repository.user.User
import ui.components.*
import ui.screens.SecVaultScreen
import ui.theme.Font
import ui.theme.primary
import ui.theme.secondary
import ui.theme.tertiary
import ui.validators.CreditCardFormFieldName
import ui.validators.creditCardFormValidator
import ui.validators.toCreditCardDto
import viewmodel.PasswordMgntScreenModel
import kotlin.time.Duration.Companion.seconds

class CreditCardForm(creditCard: CreditCard?, formType: FormType) : Screen {

    private val _creditCard = creditCard
    private val _formType = formType

    @Composable
    override fun Content() {

        val screenModel = koinScreenModel<PasswordMgntScreenModel>()
        val formValidator = remember { creditCardFormValidator(_creditCard, screenModel::decryptPassword) }
        val passwordState by screenModel.passwordState.collectAsState()
        val navigator = LocalNavigator.current
        val toaster = rememberToasterState()

        Toaster(
            state = toaster,
            alignment = Alignment.TopEnd,
            darkTheme = true,
            showCloseButton = true,
            richColors = true
        )

        CreditCardFormContent(
            formValidator,
            screenModel,
            navigator,
            _formType
        )

        when (val state = passwordState) {
            is UiState.Loading -> LoadingScreen(backgroundColor = tertiary.copy(alpha = 0.8f))
            is UiState.Success -> {
                LaunchedEffect(state) {
                    toaster.show(
                        message = "Credit Card was successfully added",
                        type = ToastType.Success,
                        duration = 2.seconds
                    )
                    delay(500)
                    navigator?.popUntil { screen: Screen -> screen.key == SecVaultScreen().key }
                }
            }

            is UiState.Error -> {
                LaunchedEffect(toaster) {
                    toaster.show(
                        message = state.message,
                        type = ToastType.Error,
                        duration = 5.seconds
                    )
                    screenModel.clearError()
                }
            }

            is UiState.Idle -> {}
        }

    }

    @Composable
    fun CreditCardFormContent(
        formValidator: FormValidator,
        screenModel: PasswordMgntScreenModel,
        navigator: Navigator?,
        formType: FormType
    ) {

        val isFormValid by formValidator.isValid
        val users by remember { mutableStateOf<List<User>?>(screenModel.fetchUsers()) }
        var selectedItem by remember { mutableStateOf<DropdownItem<User>?>(null) }

        val cardBank = formValidator.getField(CreditCardFormFieldName.CARD_NAME)
        val cardOwner = formValidator.getField(CreditCardFormFieldName.CARD_OWNER)
        val cardNumber = formValidator.getField(CreditCardFormFieldName.CARD_NUMBER)
        val cvc = formValidator.getField(CreditCardFormFieldName.CARD_CVC)
        val pin = formValidator.getField(CreditCardFormFieldName.CARD_PIN)
        val expiry = formValidator.getField(CreditCardFormFieldName.CARD_EXPIRY)
        val notes = formValidator.getField(CreditCardFormFieldName.CARD_NOTES)

        users?.find {
            it.id.value.toString() == cardOwner?.value?.value
        }?.let {
            selectedItem = DropdownItem(it, it.userName)
        }

        LaunchedEffect(selectedItem) {
            selectedItem?.let {
                cardOwner?.value?.value = it.id.toString()
                formValidator.validateField(CreditCardFormFieldName.CARD_OWNER)
            }
        }

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
                    creditCardButtonShown = false,
                    notesButtonShown = (formType == CREATION),
                    title = if (formType == CREATION) "Create Credit Card" else "Update Credit Card"
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
                                value = cardBank?.value?.value ?: "",
                                onValueChange = { newValue ->
                                    cardBank?.value?.value = newValue
                                    formValidator.validateField(CreditCardFormFieldName.CARD_NAME)
                                },
                                label = CreditCardFormFieldName.CARD_NAME.fieldName,
                                icon = Icons.Filled.Money,
                                modifier = Modifier.height(45.dp).width(400.dp)
                            )
                            cardBank?.error?.value?.let {
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
                            FormDropdown(
                                items = users?.map { DropdownItem(it, it.userName) } ?: emptyList(),
                                selectedItem = selectedItem,
                                onItemSelected = { selectedItem = it },
                                modifier = Modifier.height(45.dp).width(400.dp),
                                label = CreditCardFormFieldName.CARD_OWNER.fieldName,
                                icon = Icons.Filled.VerifiedUser
                            )
                            cardOwner?.error?.value?.let {
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
                                value = cardNumber?.value?.value ?: "",
                                onValueChange = { newValue ->
                                    cardNumber?.value?.value = newValue
                                    formValidator.validateField(CreditCardFormFieldName.CARD_NUMBER)
                                },
                                label = CreditCardFormFieldName.CARD_NUMBER.fieldName,
                                icon = Icons.Filled.CreditCard,
                                modifier = Modifier.height(45.dp).width(400.dp)
                            )
                            cardNumber?.error?.value?.let {
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
                                value = cvc?.value?.value ?: "",
                                onValueChange = { newValue ->
                                    cvc?.value?.value = newValue
                                    formValidator.validateField(CreditCardFormFieldName.CARD_CVC)
                                },
                                label = CreditCardFormFieldName.CARD_CVC.fieldName,
                                icon = Icons.Filled.Pin,
                                modifier = Modifier.height(45.dp).width(400.dp)
                            )
                            cvc?.error?.value?.let {
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
                                value = pin?.value?.value ?: "",
                                onValueChange = { newValue ->
                                    pin?.value?.value = newValue
                                    formValidator.validateField(CreditCardFormFieldName.CARD_PIN)
                                },
                                label = CreditCardFormFieldName.CARD_PIN.fieldName,
                                icon = Icons.Filled.Pin,
                                modifier = Modifier.height(45.dp).width(400.dp)
                            )
                            pin?.error?.value?.let {
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
                                value =
                                    expiry?.value?.value ?: "",
                                onValueChange = { newValue ->
                                    expiry?.value?.value = newValue
                                    formValidator.validateField(CreditCardFormFieldName.CARD_EXPIRY)
                                },
                                label = CreditCardFormFieldName.CARD_EXPIRY.fieldName,
                                icon = Icons.Filled.DateRange,
                                modifier = Modifier.height(45.dp).width(400.dp)
                            )
                            expiry?.error?.value?.let {
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
                        Column(modifier = Modifier.height(120.dp)) {
                            FormTextArea(
                                value = notes?.value?.value ?: "",
                                onValueChange = { newValue ->
                                    notes?.value?.value = newValue
                                    formValidator.validateField(CreditCardFormFieldName.CARD_NOTES)
                                },
                                label = CreditCardFormFieldName.CARD_NOTES.fieldName,
                                modifier = Modifier.width(400.dp)
                            )
                            notes?.error?.value?.let {
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
                    {
                        screenModel.saveCreditCard(
                            _creditCard?.id?.value,
                            toCreditCardDto(
                                formValidator,
                                SecurityContext.authenticatedUser!!,
                            selectedItem?.id!!
                            ),
                            formType
                        )
                    },
                    { navigator?.popUntil { screen: Screen -> screen.key == SecVaultScreen().key } },
                    isFormValid
                )
            }

        }
    }

}