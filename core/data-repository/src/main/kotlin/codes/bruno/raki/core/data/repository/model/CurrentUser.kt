package codes.bruno.raki.core.data.repository.model

import codes.bruno.raki.core.data.datastore.model.CurrentUser as DatastoreCurrentUser
import codes.bruno.raki.core.domain.model.CurrentUser as DomainCurrentUser

fun DatastoreCurrentUser.asDomainModel() = DomainCurrentUser(
    domain = domain,
    tokenType = if (hasTokenType()) tokenType else null,
    accessToken = if (hasAccessToken()) accessToken else null,
)