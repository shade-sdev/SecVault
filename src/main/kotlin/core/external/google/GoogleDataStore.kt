package core.external.google

import com.google.api.client.util.store.DataStore
import com.google.api.client.util.store.DataStoreFactory
import org.jetbrains.exposed.sql.statements.api.ExposedBlob
import org.jetbrains.exposed.sql.transactions.transaction
import repository.google.GoogleDriveConfig
import repository.google.GoogleDriveConfigTable
import java.io.*
import java.util.*

class GoogleDataStore<V : Serializable>(private val id: String) : DataStore<V> {

    override fun getDataStoreFactory(): DataStoreFactory {
        return DatabaseDataStoreFactory()
    }

    override fun getId(): String = id

    override fun size(): Int = transaction {
        GoogleDriveConfig.find { GoogleDriveConfigTable.id eq UUID.fromString(id) }
                .count()
    }.toInt()

    override fun isEmpty(): Boolean = size() == 0

    override fun containsKey(key: String): Boolean = transaction {
        GoogleDriveConfig.findById(UUID.fromString(key)) != null
    }

    override fun containsValue(value: V): Boolean = transaction {
        GoogleDriveConfig.all()
                .any { config ->
                    config.credential.inputStream.use {
                        ObjectInputStream(it).readObject() == value
                    }
                }
    }

    override fun keySet(): Set<String> = transaction {
        GoogleDriveConfig.all()
                .map { it.id.value.toString() }
                .toSet()
    }

    override fun values(): Collection<V?> = transaction {
        keySet().map { get(it) }
    }

    @Suppress("UNCHECKED_CAST")
    override fun get(key: String): V? = transaction {
        val config = GoogleDriveConfig.findById(UUID.fromString(key))
        val credentialBlob = config?.credential
        if (credentialBlob == null || credentialBlob.bytes.isEmpty()) {
            return@transaction null
        }

        credentialBlob.inputStream.use {
            ObjectInputStream(it).readObject() as? V
        }
    }

    override fun set(
        key: String,
        value: V
    ): DataStore<V> {
        transaction {
            val config = GoogleDriveConfig.findById(UUID.fromString(key))
            val serializedCredential = ByteArrayOutputStream().use { byteOut ->
                ObjectOutputStream(byteOut).use { it.writeObject(value) }
                byteOut.toByteArray()
            }

            if (config != null) {
                config.credential = ExposedBlob(serializedCredential)
            } else {
                GoogleDriveConfig.new(UUID.fromString(key)) {
                    this.credential = ExposedBlob(serializedCredential)
                }
            }
        }
        return this
    }

    override fun clear(): DataStore<V> {
        transaction {
            GoogleDriveConfig.all()
                    .forEach { it.credential = ExposedBlob(ByteArrayInputStream(ByteArray(0))) }
        }
        return this
    }

    override fun delete(key: String): DataStore<V> {
        transaction {
            GoogleDriveConfig.findById(UUID.fromString(key))
                    ?.delete()
        }
        return this
    }

}