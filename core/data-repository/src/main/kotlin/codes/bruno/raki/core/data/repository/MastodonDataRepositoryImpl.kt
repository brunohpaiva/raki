package codes.bruno.raki.core.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import codes.bruno.raki.core.data.database.TimelineDataSource
import codes.bruno.raki.core.data.network.MastodonApiDataSource
import codes.bruno.raki.core.data.repository.model.asDomainModel
import codes.bruno.raki.core.domain.repository.MastodonDataRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
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
