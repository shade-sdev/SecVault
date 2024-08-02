package di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent
import org.slf4j.Logger
import org.slf4j.LoggerFactory

val appModule = module {
    factory { (clazz: Class<*>) ->
        LoggerFactory.getLogger(clazz)
    }
}

@Composable
fun rememberLogger(clazz: Class<*>): Logger {
    return remember {
        KoinJavaComponent.get(Logger::class.java) {
            parametersOf(clazz)
        }
    }
}

