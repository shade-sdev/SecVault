package repository.user.impl

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.transactions.transaction
import repository.Result
import repository.errors.DatabaseError
import repository.queries.user.UserQueries
import repository.user.User
import repository.user.UserRepository
import repository.user.UsersTable

class UserRepositoryImpl(private val db: Database) : UserRepository {

    override fun findAll(): List<User> {
        return transaction(db) {
            User.all().toList()
        }
    }

    override fun hasUser(): Boolean {
        return transaction(db) {
            exec(UserQueries.USER_EXIST_QUERY) { rs ->
                rs.next()
                rs.getBoolean(1)
            } == true
        }
    }

    override suspend fun findByUsernameAndPassword(username: String, password: String): Result<User> {
        return try {
            return transaction(db) {
                User.find {
                    (UsersTable.userName eq username) and (UsersTable.password eq password)
                }.firstOrNull()
            }?.let { Result.Success(it) } ?: Result.Error("Invalid credentials")
        } catch (e: Exception) {
            Result.Error(DatabaseError.fromException(e).extractMessage())
        }
    }

    override suspend fun createUser(username: String, email: String, password: String): Result<User> {
        return try {
            return transaction(db) {
                User.new {
                    this.userName = username
                    this.email = email
                    this.password = password
                    this.createdBy = "system"
                    this.lastUpdatedBy = "system"
                }.let { Result.Success(it) }
            }
        } catch (e: Exception) {
            Result.Error(DatabaseError.fromException(e).extractMessage())
        }
    }

}