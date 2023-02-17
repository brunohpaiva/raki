package codes.bruno.raki.data.remote.source

import codes.bruno.raki.data.remote.retrofit.MastodonService

import codes.bruno.raki.data.remote.mapper.MastodonAppMapper
import codes.bruno.raki.domain.model.MastodonApp
import javax.inject.Inject

internal class RemoteAuthDataDataSource @Inject constructor(
    private val mastodonService: MastodonService,
    private val mastodonAppMapper: MastodonAppMapper,
) {

    suspend fun createMastodonApp(
        domain: String,
        clientName: String,
        redirectUris: String,
        scopes: List<String> = emptyList(),
        website: String? = null,
    ): MastodonApp {
        val remoteMastodonApp = mastodonService.createApp(
            domain,
            clientName,
            redirectUris,
            scopes.joinToString(" "),
            website,
        )

        // TODO: better way to handle this mapping?
        return mastodonAppMapper.toDomainModel(remoteMastodonApp).copy(domain = domain)
    }

}