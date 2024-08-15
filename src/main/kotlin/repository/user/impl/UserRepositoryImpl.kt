package repository.user.impl

import core.Result
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.lowerCase
import org.jetbrains.exposed.sql.transactions.transaction
import org.slf4j.Logger
import repository.errors.DatabaseError
import repository.queries.user.UserQueries
import repository.user.User
import repository.user.UserRepository
import repository.user.UsersTable
import repository.user.projection.UserSummary
import java.time.LocalDateTime
import java.util.*

class UserRepositoryImpl(
    private val db: Database,
    private val logger: Logger
) : UserRepository {

    override fun findAll(): List<User> {
        return transaction(db) {
            User.all().toList()
        }
    }

    override fun findById(id: UUID): User? {
        return transaction(db) {
            User.findById(id)
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

    override suspend fun findByUsername(username: String): Result<User> {
        return try {
            return transaction(db) {
                User.find {
                    (UsersTable.userName eq username)
                }.firstOrNull()
            }?.let { Result.Success(it) } ?: Result.Error("Invalid credentials")
        } catch (e: Exception) {
            Result.Error(DatabaseError.fromException(e).extractMessage())
        }
    }

    override suspend fun findByEmail(email: String): Result<User> {
        return try {
            return transaction(db) {
                User.find {
                    (UsersTable.email.lowerCase() eq email.lowercase())
                }.firstOrNull()
            }?.let { Result.Success(it) } ?: Result.Error("User with email not found")
        } catch (e: Exception) {
            Result.Error(DatabaseError.fromException(e).extractMessage())
        }
    }

    override suspend fun updatePassword(user: User, newPassword: String): Result<User> {
        return try {
            return transaction(db) {
                user.password = newPassword
                user.lastUpdatedBy = "system"
                user.lastUpdateDateTime = LocalDateTime.now()
                user.version += 1
                Result.Success(user)
            }
        } catch (e: Exception) {
            logger.error(e.message, e)
            Result.Error(DatabaseError.fromException(e).extractMessage())
        }
    }

    override suspend fun createUser(
        username: String,
        email: String,
        password: String,
        secretKey: String
    ): Result<UserSummary> {
        return try {
            return transaction(db) {
                User.new {
                    this.userName = username
                    this.email = email
                    this.password = password
                    this.secretKey = secretKey
                    this.createdBy = "system"
                    this.lastUpdatedBy = "system"
                }.let {
                    Result.Success(
                        UserSummary(
                            it.id.value,
                            it.userName,
                            it.email,
                            it.secretKey
                        )
                    )
                }
            }
        } catch (e: Exception) {
            logger.error(e.message, e)
            Result.Error(DatabaseError.fromException(e).extractMessage())
        }
    }

}