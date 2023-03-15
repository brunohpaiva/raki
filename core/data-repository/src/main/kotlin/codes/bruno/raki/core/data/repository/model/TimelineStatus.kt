package codes.bruno.raki.core.data.repository.model

import codes.bruno.raki.core.data.database.entity.TimelineStatus as DatabaseTimelineStatus
import codes.bruno.raki.core.data.database.entity.Visibility as DatabaseVisibility
import codes.bruno.raki.core.domain.model.TimelineStatus as DomainTimelineStatus
import codes.bruno.raki.core.domain.model.Visibility as DomainVisibility

fun DatabaseTimelineStatus.asDomainModel() = DomainTimelineStatus(
    id = status.id,
    createdAt = status.createdAt,
    authorDisplayName = author.displayName,
    authorAvatarUrl = author.avatarUrl,
    content = status.content,
    visibility = status.visibility.asDomainModel(),
    sensitive = status.sensitive,
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