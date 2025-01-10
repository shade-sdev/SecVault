package core

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import core.configs.DatabaseConfig
import core.configs.JwtConfig

/**
 * Data class representing the configuration settings for the application.
 *
 * @property database The database configuration settings.
 * @property jwt The JWT configuration settings.
 */
data class Config(
    val database: DatabaseConfig,
    val jwt: JwtConfig
)

/**
 * Loads the configuration settings from the `application.yaml` file.
 *
 * @return The loaded configuration settings.
 * @throws IllegalArgumentException if the `application.yaml` resource is not found.
 */
fun loadConfigs(): Config {
    val mapper = ObjectMapper(YAMLFactory()).apply {
        registerModule(KotlinModule.Builder().build())
    }

    val resourceStream = Config::class.java.classLoader
        .getResourceAsStream("application.yaml")
        ?: throw IllegalArgumentException("Resource not found: application.yaml")

    return mapper.readValue(resourceStream, Config::class.java)
}