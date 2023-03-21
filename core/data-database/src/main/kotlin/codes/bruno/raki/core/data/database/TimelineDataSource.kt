package codes.bruno.raki.core.data.database

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.room.paging.util.ThreadSafeInvalidationObserver
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
        return StatusPagingSource(db, dao)
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

internal class StatusPagingSource(
    private val db: AppDatabase,
    private val dao: TimelineDao,
) : PagingSource<String, TimelineStatus>() {

    private val observer = ThreadSafeInvalidationObserver(
        tables = arrayOf("status", "account"),
        onInvalidated = ::invalidate,
    )

    override suspend fun load(params: LoadParams<String>): LoadResult<String, TimelineStatus> {
        observer.registerIfNecessary(db)

        return try {
            when (params) {
                is LoadParams.Refresh -> {
                    val list = dao.list(params.loadSize)
                    LoadResult.Page(
                        data = list,
                        prevKey = null,
                        nextKey = list.lastOrNull()?.status?.id,
                    ).also {
                        Log.d(
                            "Raki",
                            "StatusPagingSource > load: REFRESH returned prevKey ${it.prevKey} nextKey ${it.nextKey}"
                        )
                    }
                }

                is LoadParams.Prepend -> {
                    Log.d("Raki", "StatusPagingSource > load: PREPEND key ${params.key}")
                    val list = dao.listNewer(key = params.key, limit = params.loadSize)
                    LoadResult.Page(
                        data = list,
                        prevKey = list.firstOrNull()?.status?.id,
                        nextKey = list.lastOrNull()?.status?.id,
                    ).also {
                        Log.d(
                            "Raki",
                            "StatusPagingSource > load: PREPEND returned prevKey ${it.prevKey} nextKey ${it.nextKey}"
                        )
                    }
                }

                is LoadParams.Append -> {
                    Log.d("Raki", "StatusPagingSource > load: APPEND key ${params.key}")
                    val list = dao.listOlder(key = params.key, limit = params.loadSize)
                    LoadResult.Page(
                        data = list,
                        prevKey = list.firstOrNull()?.status?.id,
                        nextKey = list.lastOrNull()?.status?.id,
                    ).also {
                        Log.d(
                            "Raki",
                            "StatusPagingSource > load: APPEND returned prevKey ${it.prevKey} nextKey ${it.nextKey}"
                        )
                    }
                }
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, TimelineStatus>): String? {
        // TODO
        return null
    }

}