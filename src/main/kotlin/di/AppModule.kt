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
import repository.user.UserRepository
import repository.user.impl.UserRepositoryImpl
import viewmodel.ForgotPasswordScreenModel
import viewmodel.LoginScreenModel
import viewmodel.RegisterScreenModel

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

}

val viewModelModule = module {

    factory { LoginScreenModel(get()) }

    factory { RegisterScreenModel(get()) }

    factory { ForgotPasswordScreenModel(get()) }

}

@Composable
fun rememberLogger(clazz: Class<*>): Logger {
    return remember {
        KoinJavaComponent.get(Logger::class.java) {
            parametersOf(clazz)
        }
    }
}

