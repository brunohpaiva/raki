package codes.bruno.raki.core.data.repository.model

import codes.bruno.raki.core.data.database.entity.Status as DatabaseStatus
import codes.bruno.raki.core.data.database.entity.Visibility as DatabaseVisibility
import codes.bruno.raki.core.data.network.model.Status as NetworkStatus
import codes.bruno.raki.core.data.network.model.Visibility as NetworkVisibility

fun NetworkStatus.asDatabaseModel() = DatabaseStatus(
    id = id,
    uri = uri,
    createdAt = created_at,
    authorId = account.id,
    content = content,
    visibility = visibility.asDatabaseModel(),
    sensitive = sensitive,
    reblogsCount = reblogs_count,
    favouritesCount = favourites_count,
    repliesCount = replies_count,
    favourited = favourited,
    bookmarked = bookmarked,
)

fun NetworkVisibility.asDatabaseModel() = when (this) {
    NetworkVisibility.PUBLIC -> DatabaseVisibility.PUBLIC
    NetworkVisibility.UNLISTED -> DatabaseVisibility.UNLISTED
    NetworkVisibility.PRIVATE -> DatabaseVisibility.PRIVATE
    NetworkVisibility.DIRECT -> DatabaseVisibility.DIRECT
}
