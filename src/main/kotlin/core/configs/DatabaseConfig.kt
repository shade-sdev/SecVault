package core.configs

data class DatabaseConfig(
    val driverClassName: String,
    val jdbcUrl: String,
    val username: String,
    val password: String,
    val maximumPoolSize: Int,
    val isAutoCommit: Boolean,
    val isReadOnly: Boolean,
    val transactionIsolation: String,
)