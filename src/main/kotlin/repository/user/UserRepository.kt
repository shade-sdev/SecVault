package repository.user

import repository.Result
import java.util.*

interface UserRepository {

    fun findAll(): List<User>

    fun findById(id: UUID): User?

    fun hasUser(): Boolean

    suspend fun findByUsername(username: String): Result<User>

    suspend fun createUser(username: String, email: String, password: String): Result<User>
}