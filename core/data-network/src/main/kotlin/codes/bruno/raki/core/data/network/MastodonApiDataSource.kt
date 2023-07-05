package codes.bruno.raki.core.data.network

import codes.bruno.raki.core.data.network.model.Account
import codes.bruno.raki.core.data.network.model.MastodonApp
import codes.bruno.raki.core.data.network.model.OAuthToken
import codes.bruno.raki.core.data.network.model.Status
import codes.bruno.raki.core.data.network.model.Timeline

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

    suspend fun fetchTimeline(
        limit: Int = 20,
        minId: String? = null,
        maxId: String? = null,
        sinceId: String? = null,
    ): Timeline

    suspend fun favouriteStatus(
        id: String,
    ): Status

    suspend fun unfavouriteStatus(
        id: String,
    ): Status

}