package repository

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.javatime.datetime
import repository.common.expression.CurrentDateTime
import java.time.LocalDateTime

abstract class AuditableTable(name: String) : UUIDTable(name) {
    val creationDateTime: Column<LocalDateTime> = datetime("creation_date_time").defaultExpression(CurrentDateTime)
    val createdBy: Column<String> = varchar("created_by", 255)
    val lastUpdateDateTime: Column<LocalDateTime> = datetime("last_update_date_time").defaultExpression(CurrentDateTime)
    val lastUpdatedBy: Column<String> = varchar("last_updated_by", 255)
    val version: Column<Int> = integer("version").default(0)
}