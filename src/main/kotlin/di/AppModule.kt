package di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import core.AppState
import core.DatabaseFactory
import core.external.google.GoogleAuthManager
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
import repository.google.GoogleDriveConfig
import repository.google.GoogleDriveConfigRepository
import repository.google.impl.GoogleDriveConfigRepositoryImpl
import repository.password.PasswordRepository
import repository.password.impl.PasswordRepositoryImpl
import repository.user.UserRepository
import repository.user.impl.UserRepositoryImpl
import viewmodel.*

/**
 * Koin module for application-level dependencies.
 */
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

    single {
        AuthenticationManager(
            get(),
            get(),
            get(),
            get(),
            get(),
            get { parametersOf(AuthenticationManager::class.java) })
    } withOptions {
        createdAtStart()
    }

    single { GoogleAuthManager(get { parametersOf(GoogleAuthManager::class.java) }) }

    factory { (clazz: Class<*>) ->
        LoggerFactory.getLogger(clazz)
    }

}

/**
 * Koin module for repository dependencies.
 */
val repositoryModule = module {

    single<UserRepository> {
        UserRepositoryImpl(
            get(),
            get { parametersOf(UserRepository::class.java) })
    }

    single<PasswordRepository> {
        PasswordRepositoryImpl(
            get(),
            get { parametersOf(PasswordRepository::class.java) })
    }

    single<CreditCardRepository> {
        CreditCardRepositoryImpl(
            get(),
            get { parametersOf(CreditCardRepository::class.java) })
    }

    single<GoogleDriveConfigRepository> {
        GoogleDriveConfigRepositoryImpl(
            get(),
            get { parametersOf(GoogleDriveConfig::class.java) })
    }

}

/**
 * Koin module for ViewModel dependencies.
 */
val viewModelModule = module {

    factory { LoginScreenModel(get()) }

    factory { RegisterScreenModel(get()) }

    factory { ForgotPasswordScreenModel(get()) }

    factory {
        SecVaultScreenModel(
            get(),
            get(),
            get(),
            get(),
            get { parametersOf(SecVaultScreenModel::class.java) })
    }

    factory {
        PasswordMgntScreenModel(
            get(),
            get(),
            get(),
            get()
        )
    }

    factory {
        SettingScreenModel(
            get(),
            get()
        )
    }

}

/**
 * Remembers and provides a logger for the given class.
 *
 * @param clazz The class for which the logger is to be provided.
 * @return The logger for the given class.
 */
@Composable
fun rememberLogger(clazz: Class<*>): Logger {
    return remember {
        KoinJavaComponent.get(Logger::class.java) {
            parametersOf(clazz)
        }
    }
}