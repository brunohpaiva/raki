package codes.bruno.raki.core.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import java.time.OffsetDateTime

@Entity(
    tableName = "status",
    foreignKeys = [
        ForeignKey(childColumns = ["author_id"], parentColumns = ["id"], entity = Account::class),
        ForeignKey(
            childColumns = ["reblogged_by_author_id"],
            parentColumns = ["id"],
            entity = Account::class,
        ),
    ],
    indices = [Index("author_id"), Index("reblogged_by_author_id")],
)
data class Status(
    @PrimaryKey
    val id: String,
    val uri: String,
    @ColumnInfo(name = "created_at")
    val createdAt: OffsetDateTime,
    @ColumnInfo(name = "author_id")
    val authorId: String,
    val content: String,
    @field:TypeConverters(VisibilityTypeConverter::class)
    val visibility: Visibility,
    val sensitive: Boolean,
    @ColumnInfo(name = "reblogs_count")
    val reblogsCount: Int,
    @ColumnInfo(name = "favourites_count")
    val favouritesCount: Int,
    @ColumnInfo(name = "replies_count")
    val repliesCount: Int,
    @ColumnInfo(name = "reblogged_status_id")
    val rebloggedStatusId: String? = null,
    @ColumnInfo(name = "reblogged_by_author_id")
    val rebloggedByAuthorId: String? = null,
)

enum class Visibility(val id: Int) {
    PUBLIC(0),
    UNLISTED(1),
    PRIVATE(2),
    DIRECT(3),
}

internal object VisibilityTypeConverter {
    @TypeConverter
    fun toInt(value: Visibility) = value.id

    private val visibilityById by lazy {
        Visibility.values().associateBy { it.id }
    }

    @TypeConverter
    fun toVisibility(value: Int) = visibilityById[value]!!
}