package codes.bruno.raki.core.data.repository.model

import codes.bruno.raki.core.data.database.entity.MediaAttachmentType
import codes.bruno.raki.core.data.database.entity.MediaAttachmentType as DatabaseMediaAttachmentType
import codes.bruno.raki.core.data.database.entity.StatusMediaAttachment as DatabaseStatusMediaAttachment
import codes.bruno.raki.core.data.database.entity.TimelineStatus as DatabaseTimelineStatus
import codes.bruno.raki.core.data.database.entity.Visibility as DatabaseVisibility
import codes.bruno.raki.core.domain.model.MediaAttachment as DomainMediaAttachment
import codes.bruno.raki.core.domain.model.MediaAttachmentType as DomainMediaAttachmentType
import codes.bruno.raki.core.domain.model.TimelineStatus as DomainTimelineStatus
import codes.bruno.raki.core.domain.model.Visibility as DomainVisibility

fun DatabaseTimelineStatus.asDomainModel() = DomainTimelineStatus(
    id = status.id,
    createdAt = status.createdAt,
    authorDisplayName = author.displayName,
    authorAcct = author.acct,
    authorAvatarUrl = author.avatarUrl,
    rebloggerDisplayName = reblogAuthor?.displayName,
    rebloggerAcct = reblogAuthor?.acct,
    rebloggerAvatarUrl = reblogAuthor?.avatarUrl,
    content = status.content,
    visibility = status.visibility.asDomainModel(),
    sensitive = status.sensitive,
    mediaAttachments = mediaAttachments.map { it.asDomainModel() },
    reblogsCount = status.reblogsCount,
    favouritesCount = status.favouritesCount,
    repliesCount = status.repliesCount,
)

fun DatabaseVisibility.asDomainModel(): DomainVisibility = when (this) {
    DatabaseVisibility.PUBLIC -> DomainVisibility.PUBLIC
    DatabaseVisibility.UNLISTED -> DomainVisibility.UNLISTED
    DatabaseVisibility.PRIVATE -> DomainVisibility.PRIVATE
    DatabaseVisibility.DIRECT -> DomainVisibility.DIRECT
}

fun DatabaseStatusMediaAttachment.asDomainModel() = DomainMediaAttachment(
    // Here we drop the status id because it does not matter for the domain layer.
    id = attachmentId,
    type = type.asDomainModel(),
    url = url,
    previewUrl = previewUrl,
    remoteUrl = remoteUrl,
    description = description,
    blurhash = blurhash,
)

fun DatabaseMediaAttachmentType.asDomainModel(): DomainMediaAttachmentType = when (this) {
    MediaAttachmentType.UNKNOWN -> DomainMediaAttachmentType.UNKNOWN
    MediaAttachmentType.IMAGE -> DomainMediaAttachmentType.IMAGE
    MediaAttachmentType.GIFV -> DomainMediaAttachmentType.GIFV
    MediaAttachmentType.VIDEO -> DomainMediaAttachmentType.VIDEO
    MediaAttachmentType.AUDIO -> DomainMediaAttachmentType.AUDIO
}