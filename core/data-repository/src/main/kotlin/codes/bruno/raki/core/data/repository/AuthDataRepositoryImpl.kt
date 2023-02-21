package codes.bruno.raki.core.data.repository

import codes.bruno.raki.core.data.datastore.AuthDataDataSource
import codes.bruno.raki.core.data.network.MastodonApiDataSource
import codes.bruno.raki.core.data.network.model.asDomainModel
import codes.bruno.raki.core.data.repository.model.asDatastoreModel
import codes.bruno.raki.core.data.repository.model.asDomainModel
import codes.bruno.raki.core.domain.model.MastodonApp
import codes.bruno.raki.core.domain.repository.AuthDataRepository
import javax.inject.Inject

internal class AuthDataRepositoryImpl @Inject constructor(
    private val dataStore: AuthDataDataSource,
    private val mastodonApi: MastodonApiDataSource,
) : AuthDataRepository {

    override suspend fun getMastodonApp(domain: String): MastodonApp? {
        return dataStore.getMastodonApp(domain)?.asDomainModel()
    }

    override suspend fun createMastodonApp(
        domain: String,
        clientName: String,
        redirectUris: String,
        scopes: List<String>,
        website: String?
    ): MastodonApp {
        val networkMastodonApp = mastodonApi.createMastodonApp(
            domain,
            clientName,
            redirectUris,
            scopes,
            website,
        )

        dataStore.saveMastodonApp(networkMastodonApp.asDatastoreModel(domain))

        return networkMastodonApp.asDomainModel(domain)
    }
}