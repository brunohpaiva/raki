package codes.bruno.raki.core.data.network.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.OffsetDateTime

@JsonClass(generateAdapter = true)
data class Status(
    val id: String,
    val uri: String,
    val created_at: OffsetDateTime,
    val account: Account,
    val content: String,
    val visibility: Visibility,
    val sensitive: Boolean,
    val spoiler_text: String,
    val media_attachments: List<MediaAttachment<*>>,
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

sealed interface MediaAttachment<Meta : MediaAttachmentMeta> {
    val id: String
    val type: MediaAttachmentType
    val url: String
    val preview_url: String
    val remote_url: String?
    val meta: Meta?
    val description: String?
    val blurhash: String
}

@JsonClass(generateAdapter = true)
data class ImageMediaAttachment(
    override val id: String,
    override val url: String,
    override val preview_url: String,
    override val remote_url: String?,
    override val meta: ImageMediaAttachmentMeta?,
    override val description: String?,
    override val blurhash: String,
) : MediaAttachment<ImageMediaAttachmentMeta> {
    override val type: MediaAttachmentType = MediaAttachmentType.IMAGE
}

@JsonClass(generateAdapter = true)
data class VideoMediaAttachment(
    override val id: String,
    override val url: String,
    override val preview_url: String,
    override val remote_url: String?,
    override val meta: VideoMediaAttachmentMeta?,
    override val description: String?,
    override val blurhash: String,
) : MediaAttachment<VideoMediaAttachmentMeta> {
    override val type: MediaAttachmentType = MediaAttachmentType.VIDEO
}

@JsonClass(generateAdapter = true)
data class GifMediaAttachment(
    override val id: String,
    override val url: String,
    override val preview_url: String,
    override val remote_url: String?,
    override val meta: GifMediaAttachmentMeta?,
    override val description: String?,
    override val blurhash: String,
) : MediaAttachment<GifMediaAttachmentMeta> {
    override val type: MediaAttachmentType = MediaAttachmentType.GIFV
}

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
sealed interface MediaAttachmentMeta

@JsonClass(generateAdapter = true)
data class ImageMediaAttachmentMeta(
    val original: Resolution,
    val small: Resolution,
    val focus: Point?,
) : MediaAttachmentMeta {
    @JsonClass(generateAdapter = true)
    data class Resolution(
        val width: Int,
        val height: Int,
        val size: String,
        val aspect: Float,
    )

    @JsonClass(generateAdapter = true)
    data class Point(
        val x: Float,
        val y: Float,
    )
}

@JsonClass(generateAdapter = true)
data class VideoMediaAttachmentMeta(
    val length: String,
    val duration: Float,
    val fps: Float,
    val size: String,
    val width: Int,
    val height: Int,
) : MediaAttachmentMeta

// TODO: gif meta
@JsonClass(generateAdapter = true)
class GifMediaAttachmentMeta : MediaAttachmentMeta {
    override fun equals(other: Any?): Boolean {
        return this === other
    }

    override fun hashCode(): Int {
        return System.identityHashCode(this)
    }
}