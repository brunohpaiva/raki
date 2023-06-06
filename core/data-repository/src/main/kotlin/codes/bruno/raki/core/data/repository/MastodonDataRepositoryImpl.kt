package codes.bruno.raki.core.data.repository

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.paging.map
import codes.bruno.raki.core.data.database.TimelineDataSource
import codes.bruno.raki.core.data.network.MastodonApiDataSource
import codes.bruno.raki.core.data.network.model.Timeline
import codes.bruno.raki.core.data.repository.model.asDatabaseModel
import codes.bruno.raki.core.data.repository.model.asDomainModel
import codes.bruno.raki.core.domain.repository.MastodonDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import codes.bruno.raki.core.data.database.entity.Account as DatabaseAccount
import codes.bruno.raki.core.data.database.entity.Status as DatabaseStatus
import codes.bruno.raki.core.data.database.entity.StatusMediaAttachment as DatabaseStatusMediaAttachment
import codes.bruno.raki.core.data.database.entity.TimelineStatus as DatabaseTimelineStatus
import codes.bruno.raki.core.domain.model.Account as DomainAccount
import codes.bruno.raki.core.domain.model.TimelineStatus as DomainTimelineStatus

internal class MastodonDataRepositoryImpl @Inject constructor(
    private val mastodonApi: MastodonApiDataSource,
    private val timelineDataSource: TimelineDataSource,
) : MastodonDataRepository {

    override suspend fun fetchAccount(): DomainAccount {
        return mastodonApi.verifyCredentials().asDomainModel()
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun viewHomeTimeline(
        limit: Int, minId: String?, maxId: String?, sinceId: String?,
    ): Flow<PagingData<DomainTimelineStatus>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = TimelineRemoteMediator(
                mastodonApi = mastodonApi,
                dataSource = timelineDataSource,
            ),
        ) {
            timelineDataSource.pagingSource()
        }.flow.map { pagingData ->
            pagingData.map { it.asDomainModel() }
        }
    }

}

@OptIn(ExperimentalPagingApi::class)
class TimelineRemoteMediator(
    private val mastodonApi: MastodonApiDataSource,
    private val dataSource: TimelineDataSource,
) : RemoteMediator<String, DatabaseTimelineStatus>() {

    override suspend fun initialize(): InitializeAction {
        // TODO: clear old statuses
        return super.initialize()
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<String, DatabaseTimelineStatus>,
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
        val statuses = mutableListOf<DatabaseStatus>()
        val mediaAttachments = mutableListOf<DatabaseStatusMediaAttachment>()
        val accounts = mutableListOf<DatabaseAccount>()

        for (status in timeline.statuses) {
            val reblog = status.reblog

            statuses += if (reblog == null) {
                status.asDatabaseModel()
            } else {
                reblog.asDatabaseModel().copy(
                    id = status.id,
                    rebloggedStatusId = reblog.id,
                    rebloggedByAuthorId = status.account.id,
                )
            }

            for (mediaAttachment in status.media_attachments) {
                mediaAttachments += mediaAttachment.asDatabaseModel(status.id)
            }

            accounts += status.account.asDatabaseModel()

            if (reblog != null) {
                // TODO: better way to handle attachments of reblogged statuses
                for (mediaAttachment in reblog.media_attachments) {
                    mediaAttachments += mediaAttachment.asDatabaseModel(reblog.id)
                }

                accounts += reblog.account.asDatabaseModel()
            }
        }

        dataSource.save(
            statuses = statuses,
            mediaAttachments = mediaAttachments,
            accounts = accounts,
            clearOld = clearOld,
        )
    }

}