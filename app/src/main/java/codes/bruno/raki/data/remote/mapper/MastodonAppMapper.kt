package codes.bruno.raki.data.remote.mapper

import javax.inject.Inject
import javax.inject.Singleton
import codes.bruno.raki.data.remote.model.MastodonApp as RemoteMastodonApp
import codes.bruno.raki.domain.model.MastodonApp as DomainMastodonApp

@Singleton
internal class MastodonAppMapper @Inject constructor() {

    fun toDomainModel(model: RemoteMastodonApp) = DomainMastodonApp(
        domain = "", // TODO: better way to handle this?
        clientId = model.client_id,
        clientSecret = model.client_secret,
    )

    fun toRemoteModel(model: DomainMastodonApp) = RemoteMastodonApp(
        client_id = model.clientId,
        client_secret = model.clientSecret,
    )

}