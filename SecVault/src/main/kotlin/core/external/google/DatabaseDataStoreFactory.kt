package core.external.google

import com.google.api.client.util.store.DataStore
import com.google.api.client.util.store.DataStoreFactory
import java.io.Serializable

/**
 * A factory for creating instances of `DataStore` backed by a Google database.
 */
class DatabaseDataStoreFactory : DataStoreFactory {
    @Suppress("UNCHECKED_CAST")
    override fun <V : Serializable?> getDataStore(id: String?): DataStore<V> {
        requireNotNull(id) { "Data store ID cannot be null" }
        return GoogleDataStore<Serializable>(id) as DataStore<V>
    }
}
