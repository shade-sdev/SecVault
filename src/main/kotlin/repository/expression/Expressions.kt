package repository.expression

import org.jetbrains.exposed.sql.Expression
import org.jetbrains.exposed.sql.QueryBuilder
import java.time.LocalDateTime

object CurrentDateTime : Expression<LocalDateTime>() {
    override fun toQueryBuilder(queryBuilder: QueryBuilder) {
        queryBuilder.append("CURRENT_TIMESTAMP")
    }
}
