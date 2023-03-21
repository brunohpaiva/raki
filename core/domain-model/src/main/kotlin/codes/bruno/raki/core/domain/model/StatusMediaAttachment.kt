package codes.bruno.raki.core.domain.model

data class StatusMediaAttachment(
    val statusId: String,
    val attachmentId: String,
    val type: String, // TODO
    val url: String,
    val previewUrl: String,
    val remoteUrl: String?,
//    val meta: Any, TODO: find a good way to store meta data
    val description: String?,
    val blurhash: String,
)