package viewmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import core.AppState
import core.models.FormType
import core.models.Result
import core.models.UiState
import core.models.dto.CreditCardDto
import core.models.dto.PasswordDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import repository.creditcard.CreditCardRepository
import repository.password.PasswordRepository
import repository.user.User
import repository.user.UserRepository
import java.util.*

class PasswordMgntScreenModel(
    private val passwordRepository: PasswordRepository,
    private val creditCardRepository: CreditCardRepository,
    private val userRepository: UserRepository,
    private val appState: AppState,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ScreenModel {

    private val _passwordState = MutableStateFlow<UiState<Boolean>>(UiState.Idle)
    val passwordState: StateFlow<UiState<Boolean>> = _passwordState.asStateFlow()

    fun savePassword(id: UUID?, password: PasswordDto, formType: FormType) {
        saveOrUpdate(
            id = id,
            dto = password.apply {
                password.password = appState.encryptString(password.password)
            },
            formType = formType,
            saveAction = passwordRepository::save,
            updateAction = passwordRepository::update,
            stateUpdater = { _passwordState.value = it }
        )
    }

    fun saveCreditCard(id: UUID?, creditCardDto: CreditCardDto, formType: FormType) {
        saveOrUpdate(
            id = id,
            dto = creditCardDto.apply {
                creditCardDto.cvc = appState.encryptString(creditCardDto.cvc)
                creditCardDto.pin = appState.encryptString(creditCardDto.pin)
            },
            formType = formType,
            saveAction = creditCardRepository::save,
            updateAction = creditCardRepository::update,
            stateUpdater = { _passwordState.value = it }
        )
    }

    fun fetchUsers(): List<User> {
        return userRepository.findAll()
    }

    fun getAuthenticatedUser(): User {
        return appState.getAuthenticatedUser!!
    }

    fun clearError() {
        _passwordState.value = UiState.Idle
    }

    fun decryptPassword(text: String?): String {
        return try {
            appState.decryptPassword(text)
        } catch (e: Exception) {
            "FUCK U"
        }
    }

    private fun <T, R> saveOrUpdate(
        id: UUID?,
        dto: T,
        formType: FormType,
        saveAction: suspend (T) -> Result<R>,
        updateAction: suspend (UUID, String, T) -> Result<R>,
        stateUpdater: (UiState<R>) -> Unit
    ) {
        screenModelScope.launch(dispatcher) {
            stateUpdater(UiState.Loading)

            val result = when (formType) {
                FormType.CREATION -> saveAction(dto)
                FormType.MODIFIATION -> updateAction(id!!, appState.userName, dto)
            }

            when (result) {
                is Result.Success -> stateUpdater(UiState.Success(result.data))
                is Result.Error -> stateUpdater(UiState.Error(result.message))
            }
        }
    }

}