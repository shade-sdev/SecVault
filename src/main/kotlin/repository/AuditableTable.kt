package repository

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.datetime
import repository.common.expression.CurrentDateTime
import java.time.LocalDateTime

/**
 * Abstract table class for auditable entities.
 *
 * @param name The name of the table.
 */
abstract class AuditableTable(name: String) : UUIDTable(name) {
    /**
     * The date and time when the entity was created.
     */
    val creationDateTime: Column<LocalDateTime> = datetime("creation_date_time").defaultExpression(CurrentDateTime)

    /**
     * The user who created the entity.
     */
    val createdBy: Column<String> = varchar("created_by", 255)

    /**
     * The date and time when the entity was last updated.
     */
    val lastUpdateDateTime: Column<LocalDateTime> = datetime("last_update_date_time").defaultExpression(CurrentDateTime)

    /**
     * The user who last updated the entity.
     */
    val lastUpdatedBy: Column<String> = varchar("last_updated_by", 255)

    /**
     * The version of the entity.
     */
    val version: Column<Int> = integer("version").default(0)
}
