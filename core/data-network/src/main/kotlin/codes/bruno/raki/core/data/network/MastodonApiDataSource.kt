package codes.bruno.raki.core.data.network

import codes.bruno.raki.core.data.network.model.MastodonApp

interface MastodonApiDataSource {

    suspend fun createMastodonApp(
        domain: String,
        clientName: String,
        redirectUris: String,
        scopes: List<String> = emptyList(),
        website: String? = null,
    ): MastodonApp

}