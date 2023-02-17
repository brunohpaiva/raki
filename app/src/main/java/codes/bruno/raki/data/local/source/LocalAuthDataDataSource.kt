package codes.bruno.raki.data.local.source

import androidx.datastore.core.DataStore
import codes.bruno.raki.data.local.mapper.MastodonAppMapper
import codes.bruno.raki.data.local.model.AuthData
import codes.bruno.raki.data.local.model.copy
import codes.bruno.raki.domain.model.MastodonApp
import kotlinx.coroutines.flow.first
import javax.inject.Inject

internal class LocalAuthDataDataSource @Inject constructor(
    private val dataStore: DataStore<AuthData>,
    private val mastodonAppMapper: MastodonAppMapper,
) {

    suspend fun getMastodonApp(domain: String): MastodonApp? {
        return dataStore.data.first().appsMap[domain]?.let {
            mastodonAppMapper.toDomainModel(it)
        }
    }

    suspend fun saveMastodonApp(mastodonApp: MastodonApp) {
        dataStore.updateData {
            it.copy {
                apps[mastodonApp.domain] = mastodonAppMapper.toLocalModel(mastodonApp)
            }
        }
    }

}