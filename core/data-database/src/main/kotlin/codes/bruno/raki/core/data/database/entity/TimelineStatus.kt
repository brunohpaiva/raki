package codes.bruno.raki.core.data.database.entity

import androidx.room.Embedded
import androidx.room.Relation

data class TimelineStatus(
    @Embedded
    val status: Status,
    @Relation(
        parentColumn = "author_id",
        entityColumn = "id",
    )
    val author: Account,
    @Relation(
        parentColumn = "id",
        entityColumn = "status_id",
    )
    val mediaAttachments: List<StatusMediaAttachment>,
)