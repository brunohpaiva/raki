package codes.bruno.raki.data.local.mapper

import codes.bruno.raki.data.local.model.mastodonApp
import javax.inject.Inject
import javax.inject.Singleton
import codes.bruno.raki.data.local.model.MastodonApp as LocalMastodonApp
import codes.bruno.raki.domain.model.MastodonApp as DomainMastodonApp

@Singleton
internal class MastodonAppMapper @Inject constructor() {

    fun toDomainModel(model: LocalMastodonApp) = DomainMastodonApp(
        domain = model.domain,
        clientId = model.clientId,
        clientSecret = model.clientSecret,
    )

    fun toLocalModel(model: DomainMastodonApp) = mastodonApp {
        domain = model.domain
        clientId = model.clientId
        clientSecret = model.clientSecret
    }

}