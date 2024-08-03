package repository.user.impl

import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction
import repository.user.User
import repository.user.UserRepository

class UserRepositoryImpl(private val db: Database) : UserRepository {

    override fun findAll(): List<User> {
        return transaction(db) {
            User.all().toList()
        }
    }

}