package core

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.DatabaseConfig
import org.jetbrains.exposed.sql.Slf4jSqlDebugLogger

/**
 * The `DatabaseFactory` object is responsible for creating and configuring the database connection.
 */
object DatabaseFactory {

    /**
     * Creates and configures a database connection using the provided configuration.
     *
     * @param config The configuration object containing database settings.
     * @return The configured `Database` instance.
     */
    fun create(config: Config): Database {
        val dbConfig = config.database

        val hikariConfig = HikariConfig().apply {
            driverClassName = dbConfig.driverClassName
            jdbcUrl = dbConfig.jdbcUrl
            username = dbConfig.username
            password = dbConfig.password
            maximumPoolSize = dbConfig.maximumPoolSize
            isAutoCommit = dbConfig.isAutoCommit
            isReadOnly = dbConfig.isReadOnly
            transactionIsolation = dbConfig.transactionIsolation
        }

        val dataSource = HikariDataSource(hikariConfig)

        Flyway.configure()
            .dataSource(dataSource)
            .locations("classpath:db/migration")
            .load()
            .migrate()

        val exposedDbConfig = DatabaseConfig {
            sqlLogger = Slf4jSqlDebugLogger
            keepLoadedReferencesOutOfTransaction = true
        }

        return Database.connect(datasource = dataSource, databaseConfig = exposedDbConfig)
    }

}