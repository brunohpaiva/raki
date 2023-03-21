package codes.bruno.raki.core.data.repository.model

import codes.bruno.raki.core.data.database.entity.StatusMediaAttachment as DatabaseStatusMediaAttachment
import codes.bruno.raki.core.data.network.model.MediaAttachment as NetworkMediaAttachment

fun NetworkMediaAttachment<*>.asDatabaseModel(statusId: String)  = DatabaseStatusMediaAttachment(
    statusId = statusId,
    attachmentId = id,
    type = type.toString(), // TODO
    url = url,
    previewUrl = preview_url,
    remoteUrl = remote_url,
    description = description,
    blurhash = blurhash,
)