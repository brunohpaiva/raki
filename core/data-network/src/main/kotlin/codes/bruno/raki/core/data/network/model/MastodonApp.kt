package codes.bruno.raki.core.data.network.model

import codes.bruno.raki.core.domain.model.MastodonApp as DomainMastodonApp

data class MastodonApp(
    val client_id: String,
    val client_secret: String,
)

fun MastodonApp.asDomainModel(domain: String) = DomainMastodonApp(
    domain = domain,
    clientId = client_id,
    clientSecret = client_secret,
)