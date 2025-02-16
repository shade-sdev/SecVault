package core.external.google

import com.google.api.client.util.store.DataStore
import com.google.api.client.util.store.DataStoreFactory
import java.io.Serializable

class DatabaseDataStoreFactory : DataStoreFactory {
    @Suppress("UNCHECKED_CAST")
    override fun <V : Serializable?> getDataStore(id: String?): DataStore<V> {
        requireNotNull(id) { "Data store ID cannot be null" }
        return GoogleDataStore<Serializable>(id) as DataStore<V>
    }
}
