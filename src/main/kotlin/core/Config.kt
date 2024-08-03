package core

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import core.configs.DatabaseConfig

data class Config(
    val database: DatabaseConfig
)

fun loadConfigs(): Config {
    val mapper = ObjectMapper(YAMLFactory()).apply {
        registerModule(KotlinModule.Builder().build())
    }

    val resourceStream = Config::class.java.classLoader
                                 .getResourceAsStream("application.yaml")
                         ?: throw IllegalArgumentException("Resource not found: application.yaml")

    return mapper.readValue(resourceStream, Config::class.java)
}