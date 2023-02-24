package codes.bruno.raki.core.data.network

import codes.bruno.raki.core.data.network.model.Account
import codes.bruno.raki.core.data.network.model.MastodonApp
import codes.bruno.raki.core.data.network.model.OAuthToken

interface MastodonApiDataSource {

    suspend fun createMastodonApp(
        domain: String,
        clientName: String,
        redirectUris: String,
        scopes: List<String> = emptyList(),
        website: String? = null,
    ): MastodonApp

    suspend fun createAccessToken(
        domain: String,
        grantType: String,
        code: String,
        clientId: String,
        clientSecret: String,
        redirectUri: String,
        scopes: List<String>,
    ): OAuthToken

    suspend fun verifyCredentials(): Account

}