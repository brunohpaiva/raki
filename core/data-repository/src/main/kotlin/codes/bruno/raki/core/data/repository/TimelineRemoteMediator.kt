package codes.bruno.raki.core.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import codes.bruno.raki.core.data.database.entity.TimelineStatus
import codes.bruno.raki.core.data.network.MastodonApiDataSource
import codes.bruno.raki.core.data.network.model.Status

@OptIn(ExperimentalPagingApi::class)
internal class TimelineRemoteMediator(
    private val mastodonApi: MastodonApiDataSource,
    private val saveStatuses: suspend (List<Status>, Boolean) -> Unit,
) : RemoteMediator<String, TimelineStatus>() {

    override suspend fun initialize(): InitializeAction {
        // TODO: clear old statuses
        return super.initialize()
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<String, TimelineStatus>,
    ): MediatorResult {
        Log.d("Raki", "RemoteMediator > load: $loadType")

        val loadKey = when (loadType) {
            LoadType.REFRESH -> null
            LoadType.PREPEND -> {
                state.firstItemOrNull()?.status?.id ?: return MediatorResult.Success(
                    endOfPaginationReached = true,
                )
            }

            LoadType.APPEND -> {
                state.lastItemOrNull()?.status?.id ?: return MediatorResult.Success(
                    endOfPaginationReached = true,
                )
            }
        }

        Log.d("Raki", "RemoteMediator > loadkey = $loadKey")
        val timeline = mastodonApi.fetchTimeline(
            limit = state.config.pageSize,
            minId = if (loadType == LoadType.PREPEND) loadKey else null,
            maxId = if (loadType == LoadType.APPEND) loadKey else null,
        )
        Log.d("Raki", "RemoteMediator > prevKey ${timeline.prevKey} nextKey ${timeline.nextKey}")

        saveStatuses(timeline.statuses, loadType == LoadType.REFRESH)

        return MediatorResult.Success(
            endOfPaginationReached = when (loadType) {
                LoadType.REFRESH -> timeline.prevKey == null && timeline.nextKey == null
                LoadType.PREPEND -> timeline.nextKey == null
                LoadType.APPEND -> timeline.prevKey == null
            },
        )
    }

}