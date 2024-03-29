package codes.bruno.raki.core.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import codes.bruno.raki.core.data.database.entity.Account
import codes.bruno.raki.core.data.database.entity.Status
import codes.bruno.raki.core.data.database.entity.StatusMediaAttachment
import codes.bruno.raki.core.data.database.entity.TimelineStatus

@Dao
internal interface TimelineDao {

    @Transaction
    @Query("SELECT * FROM status LIMIT :limit")
    suspend fun list(limit: Int): List<TimelineStatus>

    @Transaction
    @Query("SELECT * FROM status WHERE id < :key LIMIT :limit")
    suspend fun listOlder(key: String, limit: Int): List<TimelineStatus>

    @Transaction
    @Query("SELECT * FROM status WHERE id > :key LIMIT :limit")
    suspend fun listNewer(key: String, limit: Int): List<TimelineStatus>

    @Query("SELECT favourited FROM status WHERE id = :id")
    suspend fun isStatusFavourited(id: String): Boolean

    @Query("SELECT bookmarked FROM status WHERE id = :id")
    suspend fun isStatusBookmarked(id: String): Boolean

    @Upsert
    suspend fun save(
        statuses: List<Status>,
        mediaAttachments: List<StatusMediaAttachment>,
        accounts: List<Account>,
    )

    @Query("DELETE FROM status")
    suspend fun delete() // TODO

}