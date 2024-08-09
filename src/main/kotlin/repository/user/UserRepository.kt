package repository.user

import repository.Result

interface UserRepository {

    fun findAll(): List<User>

    fun hasUser(): Boolean

    suspend fun findByUsernameAndPassword(username: String, password: String): Result<User>

    suspend fun createUser(username: String, email: String, password: String): Result<User>
}