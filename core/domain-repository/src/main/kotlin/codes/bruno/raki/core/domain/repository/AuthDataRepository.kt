package codes.bruno.raki.core.domain.repository

import codes.bruno.raki.core.domain.model.MastodonApp

interface AuthDataRepository {
    suspend fun getMastodonApp(domain: String): MastodonApp?
    suspend fun createMastodonApp(
        domain: String,
        clientName: String,
        redirectUris: String,
        scopes: List<String> = emptyList(),
        website: String? = null,
    ): MastodonApp
}