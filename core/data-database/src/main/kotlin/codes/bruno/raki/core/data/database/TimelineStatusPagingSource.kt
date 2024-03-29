package codes.bruno.raki.core.data.database

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import codes.bruno.raki.core.data.database.dao.TimelineDao
import codes.bruno.raki.core.data.database.entity.TimelineStatus
import codes.bruno.raki.core.data.database.util.PagingSourceInvalidationTracker

internal class TimelineStatusPagingSource(
    private val db: AppDatabase,
    private val dao: TimelineDao,
) : PagingSource<String, TimelineStatus>() {

    // not using ThreadSafeInvalidationObserver since it's restricted to Room's library group
    private val databaseTablesObserver = PagingSourceInvalidationTracker(
        pagingSource = this,
        tables = arrayOf("status", "status_media_attachment", "account"),
    )

    override suspend fun load(params: LoadParams<String>): LoadResult<String, TimelineStatus> {
        databaseTablesObserver.register(db)

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
