package codes.bruno.raki.core.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import codes.bruno.raki.core.data.database.TimelineDataSource
import codes.bruno.raki.core.data.database.entity.TimelineStatus
import codes.bruno.raki.core.data.network.MastodonApiDataSource
import codes.bruno.raki.core.data.network.model.Timeline
import codes.bruno.raki.core.data.repository.model.asDatabaseModel
import codes.bruno.raki.core.data.database.entity.Account as DatabaseAccount
import codes.bruno.raki.core.data.database.entity.Status as DatabaseStatus
import codes.bruno.raki.core.data.database.entity.StatusMediaAttachment as DatabaseStatusMediaAttachment

@OptIn(ExperimentalPagingApi::class)
internal class TimelineRemoteMediator(
    private val mastodonApi: MastodonApiDataSource,
    private val dataSource: TimelineDataSource,
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

        save(timeline, loadType == LoadType.REFRESH)

        return MediatorResult.Success(
            endOfPaginationReached = when (loadType) {
                LoadType.REFRESH -> timeline.prevKey == null && timeline.nextKey == null
                LoadType.PREPEND -> timeline.nextKey == null
                LoadType.APPEND -> timeline.prevKey == null
            },
        )
    }

    private suspend fun save(timeline: Timeline, clearOld: Boolean) {
        val dbStatuses = mutableListOf<DatabaseStatus>()
        val dbMediaAttachments = mutableListOf<DatabaseStatusMediaAttachment>()
        val dbAccounts = mutableListOf<DatabaseAccount>()

        for (status in timeline.statuses) {
            val reblog = status.reblog

            dbStatuses += if (reblog == null) {
                status.asDatabaseModel()
            } else {
                reblog.asDatabaseModel().copy(
                    id = status.id,
                    rebloggedStatusId = reblog.id,
                    rebloggedByAuthorId = status.account.id,
                )
            }

            for (mediaAttachment in status.media_attachments) {
                dbMediaAttachments += mediaAttachment.asDatabaseModel(status.id)
            }

            dbAccounts += status.account.asDatabaseModel()

            if (reblog != null) {
                // TODO: better way to handle attachments of reblogged statuses
                for (mediaAttachment in reblog.media_attachments) {
                    dbMediaAttachments += mediaAttachment.asDatabaseModel(reblog.id)
                }

                dbAccounts += reblog.account.asDatabaseModel()
            }
        }

        dataSource.save(
            statuses = dbStatuses,
            mediaAttachments = dbMediaAttachments,
            accounts = dbAccounts,
            clearOld = clearOld,
        )
    }

}