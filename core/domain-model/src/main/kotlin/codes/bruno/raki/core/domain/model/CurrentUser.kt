package codes.bruno.raki.core.domain.model

data class CurrentUser(
    val domain: String,
    val tokenType: String?,
    val accessToken: String?,
)