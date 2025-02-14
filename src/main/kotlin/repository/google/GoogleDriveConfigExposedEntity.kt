package repository.google

import org.jetbrains.exposed.dao.Entity
import org.jetbrains.exposed.dao.EntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IdTable
import repository.user.User
import repository.user.UsersTable
import java.util.*

object GoogleDriveConfigTable : IdTable<UUID>(name = "google_drive_config") {
    override val id = uuid("user_id").entityId().references(UsersTable.id)
    val configFile = blob("config_file")
}

class GoogleDriveConfig(id: EntityID<UUID>) : Entity<UUID>(id) {
    companion object : EntityClass<UUID, GoogleDriveConfig>(GoogleDriveConfigTable)

    var user by User referencedOn GoogleDriveConfigTable.id
    var configFile by GoogleDriveConfigTable.configFile
}