package codes.bruno.raki.core.domain.model

data class MediaAttachment(
    val id: String,
    val type: MediaAttachmentType,
    val url: String,
    val previewUrl: String,
    val remoteUrl: String?,
//    val meta: Any, TODO: find a good way to store meta data
    val description: String?,
    val blurhash: String,
)

enum class MediaAttachmentType {
    UNKNOWN,
    IMAGE,
    GIFV,
    VIDEO,
    AUDIO,
}