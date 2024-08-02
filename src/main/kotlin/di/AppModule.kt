package di

import org.koin.dsl.module
import org.slf4j.LoggerFactory

val appModule = module {
    factory { (clazz: Class<*>) ->
        LoggerFactory.getLogger(clazz)
    }
}