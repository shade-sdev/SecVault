package core.configs

data class JwtConfig(val issuer: String, val secret: String, val expiration: Long)
