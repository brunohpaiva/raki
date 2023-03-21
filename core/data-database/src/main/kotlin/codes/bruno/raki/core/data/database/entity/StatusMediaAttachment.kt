package codes.bruno.raki.core.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters

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
    @field:TypeConverters(MediaAttachmentTypeTypeConverter::class)
    val type: MediaAttachmentType,
    val url: String,
    @ColumnInfo(name = "preview_url")
    val previewUrl: String,
    @ColumnInfo(name = "remote_url")
    val remoteUrl: String?,
//    val meta: Any, TODO: find a good way to store meta data
    val description: String?,
    val blurhash: String,
)

enum class MediaAttachmentType(val id: Int) {
    UNKNOWN(0),
    IMAGE(1),
    GIFV(2),
    VIDEO(3),
    AUDIO(4),
}

internal object MediaAttachmentTypeTypeConverter {
    @TypeConverter
    fun toInt(value: MediaAttachmentType) = value.id

    private val typesById by lazy {
        MediaAttachmentType.values().associateBy { it.id }
    }

    @TypeConverter
    fun toMediaAttachmentType(value: Int) = typesById[value]!!
}