package codes.bruno.raki.core.data.datastore

import androidx.datastore.core.DataStore
import codes.bruno.raki.core.data.datastore.model.AuthData
import codes.bruno.raki.core.data.datastore.model.MastodonApp
import codes.bruno.raki.core.data.datastore.model.copy
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class AuthDataDataSource @Inject constructor(
    private val dataStore: DataStore<AuthData>,
) {

    suspend fun getMastodonApp(domain: String): MastodonApp? {
        return dataStore.data.first().appsMap[domain]
    }

    suspend fun saveMastodonApp(mastodonApp: MastodonApp) {
        dataStore.updateData {
            it.copy {
                apps[mastodonApp.domain] = mastodonApp
            }
        }
    }

}