package codes.bruno.raki.core.data.repository

import codes.bruno.raki.core.data.datastore.AuthDataDataSource
import codes.bruno.raki.core.data.network.MastodonApiDataSource
import codes.bruno.raki.core.data.network.model.asDomainModel
import codes.bruno.raki.core.data.repository.model.asDatastoreModel
import codes.bruno.raki.core.data.repository.model.asDomainModel
import codes.bruno.raki.core.domain.model.CurrentUser
import codes.bruno.raki.core.domain.model.MastodonApp
import codes.bruno.raki.core.domain.model.OAuthToken
import codes.bruno.raki.core.domain.repository.AuthDataRepository
import javax.inject.Inject

internal class AuthDataRepositoryImpl @Inject constructor(
    private val dataStore: AuthDataDataSource,
    private val mastodonApi: MastodonApiDataSource,
) : AuthDataRepository {

    override suspend fun getMastodonApp(domain: String): MastodonApp? {
        return dataStore.getMastodonApp(domain)?.asDomainModel()
    }

    override suspend fun getCurrentUser(): CurrentUser? {
        return dataStore.getCurrentUser()?.asDomainModel()
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
        dataStore.saveCurrentUser(domain)

        return networkMastodonApp.asDomainModel(domain)
    }

    override suspend fun createAccessToken(
        domain: String,
        grantType: String,
        code: String,
        clientId: String,
        clientSecret: String,
        redirectUri: String,
        scopes: List<String>
    ): OAuthToken {
        val networkOAuthToken = mastodonApi.createAccessToken(
            domain, grantType, code, clientId, clientSecret, redirectUri, scopes
        )

        dataStore.saveCurrentUser(
            domain,
            networkOAuthToken.token_type,
            networkOAuthToken.access_token,
        )

        return networkOAuthToken.asDomainModel()
    }
}