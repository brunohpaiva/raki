package codes.bruno.raki.core.data.network.model

import com.squareup.moshi.Json
import java.time.OffsetDateTime

data class Status(
    val id: String,
    val uri: String,
    val created_at: OffsetDateTime,
    val account: Account,
    val content: String,
    val visibility: Visibility,
    val sensitive: Boolean,
    val spoiler_text: String,
    val media_attachments: List<MediaAttachment>,
    val reblogs_count: Int,
    val favourites_count: Int,
    val replies_count: Int,
)

enum class Visibility {
    @Json(name = "public")
    PUBLIC,
    @Json(name = "unlisted")
    UNLISTED,
    @Json(name = "private")
    PRIVATE,
    @Json(name = "direct")
    DIRECT,
}

data class MediaAttachment(
    val id: String,
    val type: MediaAttachmentType,
    val url: String,
    val preview_url: String,
    val remote_url: String?,
    val meta: Any,
    val description: String,
    val blurhash: String,
)

enum class MediaAttachmentType {
    @Json(name = "unknown")
    UNKNOWN,
    @Json(name = "image")
    IMAGE,
    @Json(name = "gifv")
    GIFV,
    @Json(name = "video")
    VIDEO,
    @Json(name = "audio")
    AUDIO,
}