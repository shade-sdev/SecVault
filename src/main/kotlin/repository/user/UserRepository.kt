package repository.user

import core.models.Result
import repository.user.projection.UserSummary
import java.util.*

interface UserRepository {

    fun findAll(): List<User>

    fun findById(id: UUID): User?

    fun hasUser(): Boolean

    fun hasUserData(userId: UUID): Boolean

    suspend fun findByUsername(username: String): Result<User>

    suspend fun findByEmail(email: String): Result<User>

    suspend fun updateUser(user: User, updates: User.() -> Unit): Result<User>

    suspend fun createUser(username: String, email: String, password: String, secretKey: String): Result<UserSummary>
}