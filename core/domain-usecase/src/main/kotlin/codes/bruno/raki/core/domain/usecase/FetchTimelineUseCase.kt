package codes.bruno.raki.core.domain.usecase

import androidx.paging.PagingData
import codes.bruno.raki.core.domain.model.TimelineStatus
import codes.bruno.raki.core.domain.repository.MastodonDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchTimelineUseCase @Inject internal constructor(
    private val mastodonDataRepository: MastodonDataRepository,
) {

    operator fun invoke(): Flow<PagingData<TimelineStatus>> {
        return mastodonDataRepository.viewHomeTimeline()
    }

}