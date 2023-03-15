package codes.bruno.raki.core.domain.repository

import androidx.paging.PagingData
import codes.bruno.raki.core.domain.model.Account
import codes.bruno.raki.core.domain.model.TimelineStatus
import kotlinx.coroutines.flow.Flow

interface MastodonDataRepository {
    suspend fun fetchAccount(): Account
    fun viewHomeTimeline(
        limit: Int = 20,
        minId: String? = null,
        maxId: String? = null,
        sinceId: String? = null,
    ): Flow<PagingData<TimelineStatus>>
}