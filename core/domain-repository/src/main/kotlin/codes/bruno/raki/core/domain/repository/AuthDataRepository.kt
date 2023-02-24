package codes.bruno.raki.core.domain.repository

import codes.bruno.raki.core.domain.model.CurrentUser
import codes.bruno.raki.core.domain.model.MastodonApp
import codes.bruno.raki.core.domain.model.OAuthToken

interface AuthDataRepository {
    suspend fun getMastodonApp(domain: String): MastodonApp?
    suspend fun getCurrentUser(): CurrentUser?
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
}