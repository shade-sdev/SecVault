package repository.user

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.Column
import repository.AuditableTable
import java.util.*

object UsersTable : AuditableTable(name = "users") {
    val userName: Column<String> = varchar("user_name", 255).uniqueIndex()
    val email: Column<String> = varchar("email", 255).uniqueIndex()
    val secretKey: Column<String> = text("secret_key")
}

class User(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<User>(UsersTable)

    var userName by UsersTable.userName
    var email by UsersTable.email
    var secretKey by UsersTable.secretKey

    var creationDateTime by UsersTable.creationDateTime
    var createdBy by UsersTable.createdBy
    var lastUpdateDateTime by UsersTable.lastUpdateDateTime
    var lastUpdatedBy by UsersTable.lastUpdatedBy
    var version by UsersTable.version

}