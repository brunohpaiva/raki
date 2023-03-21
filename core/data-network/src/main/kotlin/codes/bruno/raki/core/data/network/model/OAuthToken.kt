package codes.bruno.raki.core.data.network.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OAuthToken(
    val access_token: String,
    val token_type: String,
    val scope: String,
    val created_at: Long,
)
