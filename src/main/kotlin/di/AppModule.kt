package di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import core.AppState
import core.DatabaseFactory
import core.loadConfigs
import core.security.AuthenticationManager
import core.security.JwtService
import core.security.TwoFactorAuthenticationService
import org.koin.core.module.dsl.createdAtStart
import org.koin.core.module.dsl.withOptions
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import repository.creditcard.CreditCardRepository
import repository.creditcard.impl.CreditCardRepositoryImpl
import repository.password.PasswordRepository
import repository.password.impl.PasswordRepositoryImpl
import repository.user.UserRepository
import repository.user.impl.UserRepositoryImpl
import viewmodel.*

val appModule = module {

    single { loadConfigs() } withOptions {
        createdAtStart()
    }

    single { DatabaseFactory.create(get()) } withOptions {
        createdAtStart()
    }

    single { AppState() } withOptions {
        createdAtStart()
    }

    single { JwtService(get()) }

    single { TwoFactorAuthenticationService(get()) }

    single { AuthenticationManager(get(), get(), get(), get()) } withOptions {
        createdAtStart()
    }

    factory { (clazz: Class<*>) ->
        LoggerFactory.getLogger(clazz)
    }

}

val repositoryModule = module {

    single<UserRepository> { UserRepositoryImpl(get(), get { parametersOf(UserRepository::class.java) }) }

    single<PasswordRepository> { PasswordRepositoryImpl(get(), get { parametersOf(PasswordRepository::class.java) }) }

    single<CreditCardRepository> { CreditCardRepositoryImpl(get(), get { parametersOf(CreditCardRepository::class.java) }) }

}

val viewModelModule = module {

    factory { LoginScreenModel(get()) }

    factory { RegisterScreenModel(get()) }

    factory { ForgotPasswordScreenModel(get()) }

    factory { SecVaultScreenModel(get(), get(), get(), get { parametersOf(SecVaultScreenModel::class.java) }) }

    factory { PasswordMgntScreenModel(get(), get(), get(), get()) }

}

@Composable
fun rememberLogger(clazz: Class<*>): Logger {
    return remember {
        KoinJavaComponent.get(Logger::class.java) {
            parametersOf(clazz)
        }
    }
}

