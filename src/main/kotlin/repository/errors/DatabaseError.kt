package repository.errors

sealed class DatabaseError {
    abstract fun extractMessage(): String

    data object UniqueConstraintViolation : DatabaseError() {
        override fun extractMessage(): String {
            return "A record with the same value already exists."
        }
    }

    class NotNullConstraintViolation(private val field: String) : DatabaseError() {
        override fun extractMessage(): String {
            return "The field $field cannot be null."
        }
    }

    data object ForeignKeyConstraintViolation : DatabaseError() {
        override fun extractMessage(): String {
            return "A foreign key constraint was violated. Please ensure the related record exists."
        }
    }

    data object CheckConstraintViolation : DatabaseError() {
        override fun extractMessage(): String {
            return "A check constraint was violated. Please ensure the data meets all required conditions."
        }
    }

    data object PrimaryKeyConstraintViolation : DatabaseError() {
        override fun extractMessage(): String {
            return "A primary key constraint was violated. The primary key must be unique and not null."
        }
    }

    data object TriggerConstraintViolation : DatabaseError() {
        override fun extractMessage(): String {
            return "A trigger constraint was violated. Please check the trigger logic."
        }
    }

    data object DatabaseLocked : DatabaseError() {
        override fun extractMessage(): String {
            return "The database is locked. Please try again later."
        }
    }

    data object ReadOnlyDatabase : DatabaseError() {
        override fun extractMessage(): String {
            return "The database is in read-only mode. Write operations are not permitted."
        }
    }

    data object IOError : DatabaseError() {
        override fun extractMessage(): String {
            return "An input/output error occurred while accessing the database."
        }
    }

    data object CorruptDatabase : DatabaseError() {
        override fun extractMessage(): String {
            return "The database file is corrupt. Please restore from a backup."
        }
    }

    data object DatabaseFull : DatabaseError() {
        override fun extractMessage(): String {
            return "The database is full. Free up some space and try again."
        }
    }

    data object DataTooLarge : DatabaseError() {
        override fun extractMessage(): String {
            return "The data being stored is too large. Please reduce its size."
        }
    }

    data object OutOfMemory : DatabaseError() {
        override fun extractMessage(): String {
            return "The database ran out of memory. Please optimize your operations or increase memory."
        }
    }

    data object Misuse : DatabaseError() {
        override fun extractMessage(): String {
            return "The database was misused. Please review your database operations."
        }
    }

    data object PermissionDenied : DatabaseError() {
        override fun extractMessage(): String {
            return "Permission denied. Please ensure you have the necessary access rights."
        }
    }

    data class UnknownError(val message: String) : DatabaseError() {
        override fun extractMessage(): String {
            return "An unexpected error occurred: $message"
        }
    }

    companion object {
        fun fromException(exception: Exception): DatabaseError {
            return when {
                exception.message?.contains("UNIQUE constraint failed") == true -> {
                    UniqueConstraintViolation
                }

                exception.message?.contains("NOT NULL constraint failed") == true -> {
                    val field = exception.message?.substringAfter("NOT NULL constraint failed: ")?.substringBefore(")")
                    NotNullConstraintViolation(field ?: "unknown field")
                }

                exception.message?.contains("FOREIGN KEY constraint failed") == true -> {
                    ForeignKeyConstraintViolation
                }

                exception.message?.contains("CHECK constraint failed") == true -> {
                    CheckConstraintViolation
                }

                exception.message?.contains("SQLITE_CONSTRAINT_PRIMARYKEY") == true -> {
                    PrimaryKeyConstraintViolation
                }

                exception.message?.contains("SQLITE_CONSTRAINT_TRIGGER") == true -> {
                    TriggerConstraintViolation
                }

                exception.message?.contains("SQLITE_BUSY") == true -> {
                    DatabaseLocked
                }

                exception.message?.contains("SQLITE_READONLY") == true -> {
                    ReadOnlyDatabase
                }

                exception.message?.contains("SQLITE_IOERR") == true -> {
                    IOError
                }

                exception.message?.contains("SQLITE_CORRUPT") == true -> {
                    CorruptDatabase
                }

                exception.message?.contains("SQLITE_FULL") == true -> {
                    DatabaseFull
                }

                exception.message?.contains("SQLITE_TOOBIG") == true -> {
                    DataTooLarge
                }

                exception.message?.contains("SQLITE_NOMEM") == true -> {
                    OutOfMemory
                }

                exception.message?.contains("SQLITE_MISUSE") == true -> {
                    Misuse
                }

                exception.message?.contains("SQLITE_PERM") == true -> {
                    PermissionDenied
                }

                else -> {
                    UnknownError(exception.message ?: "Unknown error")
                }
            }
        }
    }
}
