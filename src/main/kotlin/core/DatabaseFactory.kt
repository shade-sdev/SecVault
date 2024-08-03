package core

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.flywaydb.core.Flyway
import org.jetbrains.exposed.sql.Database

object DatabaseFactory {

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

        return Database.connect(dataSource)
    }

}