package codes.bruno.raki.core.data.repository.model

import codes.bruno.raki.core.data.database.entity.MediaAttachmentType as DatabaseMediaAttachmentType
import codes.bruno.raki.core.data.database.entity.StatusMediaAttachment as DatabaseStatusMediaAttachment
import codes.bruno.raki.core.data.network.model.MediaAttachment as NetworkMediaAttachment
import codes.bruno.raki.core.data.network.model.MediaAttachmentType as NetworkMediaAttachmentType

fun NetworkMediaAttachment<*>.asDatabaseModel(statusId: String)  = DatabaseStatusMediaAttachment(
    statusId = statusId,
    attachmentId = id,
    type = type.asDatabaseModel(),
    url = url,
    previewUrl = preview_url,
    remoteUrl = remote_url,
    description = description,
    blurhash = blurhash,
)

fun NetworkMediaAttachmentType.asDatabaseModel(): DatabaseMediaAttachmentType = when (this) {
    NetworkMediaAttachmentType.UNKNOWN -> DatabaseMediaAttachmentType.UNKNOWN
    NetworkMediaAttachmentType.IMAGE -> DatabaseMediaAttachmentType.IMAGE
    NetworkMediaAttachmentType.GIFV -> DatabaseMediaAttachmentType.GIFV
    NetworkMediaAttachmentType.VIDEO -> DatabaseMediaAttachmentType.VIDEO
    NetworkMediaAttachmentType.AUDIO -> DatabaseMediaAttachmentType.AUDIO
}
