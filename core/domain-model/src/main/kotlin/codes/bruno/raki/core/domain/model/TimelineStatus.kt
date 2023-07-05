package codes.bruno.raki.core.domain.model

import java.time.OffsetDateTime

data class TimelineStatus(
    val id: String,
    val createdAt: OffsetDateTime,
    val authorDisplayName: String,
    val authorAcct: String,
    val authorAvatarUrl: String,
    val rebloggerDisplayName: String?,
    val rebloggerAcct: String?,
    val rebloggerAvatarUrl: String?,
    val content: String,
    val visibility: Visibility,
    val sensitive: Boolean,
    val mediaAttachments: List<MediaAttachment>,
    val reblogsCount: Int,
    val favouritesCount: Int,
    val repliesCount: Int,
    val favourited: Boolean,
    val bookmarked: Boolean,
)

enum class Visibility {
    PUBLIC,
    UNLISTED,
    PRIVATE,
    DIRECT,
}