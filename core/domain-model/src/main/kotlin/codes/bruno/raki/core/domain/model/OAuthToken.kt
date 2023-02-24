package codes.bruno.raki.core.domain.model

data class OAuthToken(
    val accessToken: String,
    val tokenType: String,
    val scopes: List<String>,
    // TODO: change to a Instant maybe?
    val createdAt: Long,
)