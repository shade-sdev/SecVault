package repository.user

import repository.Result

interface UserRepository {

    fun findAll(): List<User>

    suspend fun findByUsernameAndPassword(username: String, password: String): Result<User>
}