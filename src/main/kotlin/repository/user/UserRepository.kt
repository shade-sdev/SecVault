package repository.user

import repository.Result

interface UserRepository {

    fun findAll(): List<User>

    fun hasUser(): Boolean

    suspend fun findByUsernameAndPassword(username: String, password: String): Result<User>
}