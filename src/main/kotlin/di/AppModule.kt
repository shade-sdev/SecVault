package di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import core.AppState
import core.DatabaseFactory
import core.loadConfigs
import core.security.AuthenticationManager
import org.koin.core.module.dsl.createdAtStart
import org.koin.core.module.dsl.withOptions
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import repository.user.UserRepository
import repository.user.impl.UserRepositoryImpl
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

    single { AuthenticationManager(get(), get()) } withOptions {
        createdAtStart()
    }

    factory { (clazz: Class<*>) ->
        LoggerFactory.getLogger(clazz)
    }
}

val repositoryModule = module {

    single<UserRepository> { UserRepositoryImpl(get()) }

}

val viewModelModule = module {

    factory { LoginScreenModel(get()) }

    factory { RegisterScreenModel(get()) }

}

@Composable
fun rememberLogger(clazz: Class<*>): Logger {
    return remember {
        KoinJavaComponent.get(Logger::class.java) {
            parametersOf(clazz)
        }
    }
}

