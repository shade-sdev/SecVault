package core.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import core.Config
import core.configs.JwtConfig
import repository.user.User
import java.util.*

class JwtService(config: Config) {

    companion object {
        const val EMAIL = "email"
        const val USERNAME = "username"
    }

    private val jwtConfig: JwtConfig = config.jwt

    fun generateToken(user: User): String {
        return JWT.create()
                .withIssuer(jwtConfig.issuer)
                .withSubject(user.id.toString())
                .withClaim(USERNAME, user.userName)
                .withClaim(EMAIL, user.email)
                .withIssuedAt(Date(System.currentTimeMillis()))
                .withExpiresAt(Date(System.currentTimeMillis() + jwtConfig.expiration))
                .sign(Algorithm.HMAC512(jwtConfig.secret))
    }

    fun validateToken(token: String): UUID? {
        return runCatching {
            val algorithm = Algorithm.HMAC512(jwtConfig.secret)
            val verifier = JWT.require(algorithm)
                    .withIssuer(jwtConfig.issuer)
                    .build()

            verifier.verify(token).subject?.let { UUID.fromString(it) }
        }.getOrNull()
    }

}