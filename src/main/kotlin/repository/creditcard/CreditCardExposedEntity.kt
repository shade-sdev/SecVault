package repository.creditcard

import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import repository.AuditableTable
import repository.user.User
import repository.user.UsersTable
import java.util.*

object CreditCardTable : AuditableTable(name = "credit_cards") {
    val user = reference("user_id", UsersTable)
    val name = varchar("name", length = 255)
    val owner = reference("owner_id", UsersTable)
    val number = varchar("number", length = 255)
    val cvc = integer("cvc").nullable()
    val pin = integer("pin").nullable()
    val expiryDate = varchar("expiry_date", length = 5)
    val notes = text("notes").nullable()
    val favorite = bool("favorite").default(false)
    val deleted = bool("deleted").default(false)
}

class CreditCard(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<CreditCard>(CreditCardTable)

    var user by User referencedOn CreditCardTable.user
    var name by CreditCardTable.name
    var owner by User referencedOn CreditCardTable.owner
    var number by CreditCardTable.number
    var cvc by CreditCardTable.cvc
    var pin by CreditCardTable.pin
    var expiryDate by CreditCardTable.expiryDate
    var notes by CreditCardTable.notes
    var favorite by CreditCardTable.favorite
    var deleted by CreditCardTable.deleted

    var creationDateTime by CreditCardTable.creationDateTime
    var createdBy by CreditCardTable.createdBy
    var lastUpdateDateTime by CreditCardTable.lastUpdateDateTime
    var lastUpdatedBy by CreditCardTable.lastUpdatedBy
    var version by CreditCardTable.version

    fun favorite(): CreditCard {
        return this.apply {
            favorite = true
        }
    }

}