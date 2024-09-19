package repository.password

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import repository.AuditableTable
import repository.user.User
import repository.user.UsersTable
import java.util.*

object PasswordsTable : AuditableTable(name = "passwords") {
    val user = reference("user_id", UsersTable)
    val name = varchar("name", length = 255)
    val username = varchar("username", length = 255).nullable()
    val email = varchar("email", length = 255).nullable()
    val password = varchar("password", length = 255)
    val website = varchar("website", length = 255).nullable()
    val websiteIcon = varchar("website_icon", length = 255).nullable()
    val favorite = bool("favorite").default(false)
    val deleted = bool("deleted").default(false)
}

class Password(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<Password>(PasswordsTable)

    val user by User referencedOn PasswordsTable.user
    var name by PasswordsTable.name
    var username by PasswordsTable.username
    var email by PasswordsTable.email
    var password by PasswordsTable.password
    var website by PasswordsTable.website
    var websiteIcon by PasswordsTable.websiteIcon
    var favorite by PasswordsTable.favorite
    var deleted by PasswordsTable.deleted

    var creationDateTime by PasswordsTable.creationDateTime
    var createdBy by PasswordsTable.createdBy
    var lastUpdateDateTime by PasswordsTable.lastUpdateDateTime
    var lastUpdatedBy by PasswordsTable.lastUpdatedBy
    var version by PasswordsTable.version
}