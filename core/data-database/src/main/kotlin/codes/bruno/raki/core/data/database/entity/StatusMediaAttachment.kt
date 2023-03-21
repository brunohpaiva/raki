package codes.bruno.raki.core.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "status_media_attachment",
    foreignKeys = [
        ForeignKey(
            childColumns = ["status_id"],
            parentColumns = ["id"],
            entity = Status::class,
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    primaryKeys = ["status_id", "attachment_id"],
)
data class StatusMediaAttachment(
    @ColumnInfo(name = "status_id")
    val statusId: String,
    @ColumnInfo(name = "attachment_id")
    val attachmentId: String,
    val type: String, // TODO
    val url: String,
    @ColumnInfo(name = "preview_url")
    val previewUrl: String,
    @ColumnInfo(name = "remote_url")
    val remoteUrl: String?,
//    val meta: Any, TODO: find a good way to store meta data
    val description: String?,
    val blurhash: String,
)