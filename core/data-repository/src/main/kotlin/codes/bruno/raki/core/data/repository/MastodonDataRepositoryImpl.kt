package codes.bruno.raki.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import codes.bruno.raki.core.data.database.TimelineDataSource
import codes.bruno.raki.core.data.database.entity.Account
import codes.bruno.raki.core.data.database.entity.StatusMediaAttachment
import codes.bruno.raki.core.data.network.MastodonApiDataSource
import codes.bruno.raki.core.data.repository.model.asDatabaseModel
import codes.bruno.raki.core.data.repository.model.asDomainModel
import codes.bruno.raki.core.domain.repository.MastodonDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import codes.bruno.raki.core.data.database.entity.Status as DatabaseStatus
import codes.bruno.raki.core.data.network.model.Status as NetworkStatus
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
                saveStatuses = ::saveStatuses,
            ),
        ) {
            timelineDataSource.pagingSource()
        }.flow.map { pagingData ->
            pagingData.map { it.asDomainModel() }
        }
    }

    override suspend fun toggleStatusFavourite(id: String) {
        val networkStatus = if (!timelineDataSource.isStatusFavourited(id))
            mastodonApi.favouriteStatus(id)
        else
            mastodonApi.unfavouriteStatus(id)

        saveStatuses(listOf(networkStatus), clearOld = false)
    }

    private suspend fun saveStatuses(
        statuses: List<NetworkStatus>,
        clearOld: Boolean,
    ) {
        val dbStatuses = mutableListOf<DatabaseStatus>()
        val dbMediaAttachments = mutableListOf<StatusMediaAttachment>()
        val dbAccounts = mutableListOf<Account>()

        for (status in statuses) {
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

        timelineDataSource.save(
            statuses = dbStatuses,
            mediaAttachments = dbMediaAttachments,
            accounts = dbAccounts,
            clearOld = clearOld,
        )
    }
}
