package core

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import core.configs.DatabaseConfig
import core.configs.JwtConfig
import java.io.File
import java.nio.file.Files

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

    val activeProfile = System.getProperty("app.profile", "dev")

    val baseYaml = Config::class.java.classLoader.getResourceAsStream("application.yaml")
        ?.bufferedReader()?.use { it.readText() }
        ?: throw IllegalArgumentException("Base resource not found: application.yaml")

    val profileYaml = when (activeProfile) {
        "dev" -> {
            Config::class.java.classLoader.getResourceAsStream("application-dev.yaml")
                ?.bufferedReader()?.use { it.readText() }
                ?: throw IllegalArgumentException("Profile resource not found: application-dev.yaml")
        }

        "prod" -> {
            val profileFile = File("application-prod.yaml")
            if (profileFile.exists()) {
                Files.readString(profileFile.toPath())
            } else {
                throw IllegalArgumentException("Profile configuration not found: ${profileFile.absolutePath}")
            }
        }

        else -> throw IllegalArgumentException("Invalid profile: $activeProfile")
    }

    val profileConfig = mapper.readValue(profileYaml, Map::class.java)
        ?: throw IllegalArgumentException("Invalid profile YAML structure")

    if (profileConfig.all { it.key is String && it.value is Any }) {
        val typedProfileConfig = profileConfig.entries.associate { it.key as String to it.value as Any }
        val resolvedYaml = baseYaml.replacePlaceholders(typedProfileConfig)
        return mapper.readValue(resolvedYaml, Config::class.java)

    } else {
        throw IllegalArgumentException("Invalid profile YAML structure: Keys must be Strings and values must be Any")
    }

}

/**
 * Replaces placeholders in the format `${VARIABLE_NAME}` with values from the provided map.
 */
fun String.replacePlaceholders(values: Map<String, Any>): String {
    return Regex("""\$\{(.*?)}""").replace(this) { match ->
        val key = match.groupValues[1]
        values[key]?.toString() ?: throw IllegalArgumentException("Missing property: $key in profile config")
    }
}