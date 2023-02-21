package codes.bruno.raki.core.data.repository.model

import codes.bruno.raki.core.data.datastore.model.mastodonApp
import codes.bruno.raki.core.domain.model.MastodonApp
import codes.bruno.raki.core.data.datastore.model.MastodonApp as DatastoreMastodonApp
import codes.bruno.raki.core.data.network.model.MastodonApp as NetworkMastodonApp

fun NetworkMastodonApp.asDatastoreModel(domain: String) = mastodonApp {
    this.domain = domain
    clientId = client_id
    clientSecret = client_secret
}

fun DatastoreMastodonApp.asDomainModel() = MastodonApp(
    domain = domain,
    clientId = clientId,
    clientSecret = clientSecret,
)