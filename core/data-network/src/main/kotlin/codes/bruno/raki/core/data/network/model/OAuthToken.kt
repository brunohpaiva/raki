package codes.bruno.raki.core.data.network.model

import codes.bruno.raki.core.domain.model.OAuthToken as DomainOAuthToken

data class OAuthToken(
    val access_token: String,
    val token_type: String,
    val scope: String,
    val created_at: Long,
)

fun OAuthToken.asDomainModel() = DomainOAuthToken(
    accessToken = access_token,
    tokenType = token_type,
    scopes = scope.split(" "),
    createdAt = created_at,
)