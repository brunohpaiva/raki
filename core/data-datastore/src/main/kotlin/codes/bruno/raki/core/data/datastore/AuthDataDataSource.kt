package codes.bruno.raki.core.data.datastore

import androidx.datastore.core.DataStore
import codes.bruno.raki.core.data.datastore.model.AuthData
import codes.bruno.raki.core.data.datastore.model.CurrentUser
import codes.bruno.raki.core.data.datastore.model.MastodonApp
import codes.bruno.raki.core.data.datastore.model.copy
import codes.bruno.raki.core.data.datastore.model.currentUser
import codes.bruno.raki.core.data.datastore.model.currentUserOrNull
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

    suspend fun getCurrentUser(): CurrentUser? {
        return dataStore.data.first().currentUserOrNull
    }

    suspend fun saveCurrentUser(
        domain: String,
        tokenType: String? = null,
        accessToken: String? = null,
    ) {
        dataStore.updateData { currentAuthData ->
            currentAuthData.copy {
                currentUser = currentUser {
                    this.domain = domain
                    tokenType?.let { this.tokenType = it } ?: clearAccessToken()
                    accessToken?.let { this.accessToken = it } ?: clearAccessToken()
                }
            }
        }
    }

}