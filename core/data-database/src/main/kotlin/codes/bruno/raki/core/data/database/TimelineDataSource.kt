package codes.bruno.raki.core.data.database

import androidx.paging.PagingSource
import androidx.room.withTransaction
import codes.bruno.raki.core.data.database.dao.TimelineDao
import codes.bruno.raki.core.data.database.entity.Account
import codes.bruno.raki.core.data.database.entity.Status
import codes.bruno.raki.core.data.database.entity.StatusMediaAttachment
import codes.bruno.raki.core.data.database.entity.TimelineStatus
import javax.inject.Inject

class TimelineDataSource @Inject internal constructor(
    private val db: AppDatabase,
    private val dao: TimelineDao,
) {

    fun pagingSource(): PagingSource<String, TimelineStatus> {
        return TimelineStatusPagingSource(db, dao)
    }

    suspend fun isStatusFavourited(id: String): Boolean {
        return dao.isStatusFavourited(id)
    }

    suspend fun save(
        statuses: List<Status>,
        mediaAttachments: List<StatusMediaAttachment>,
        accounts: List<Account>,
        clearOld: Boolean = false, // TODO
    ) {
        db.withTransaction {
            db.openHelper.writableDatabase.execSQL("PRAGMA defer_foreign_keys = ON;")

            if (clearOld) {
                dao.delete() // TODO: pass query
            }

            dao.save(statuses, mediaAttachments, accounts)

            db.openHelper.writableDatabase.execSQL("PRAGMA defer_foreign_keys = OFF;")
        }
    }

}
