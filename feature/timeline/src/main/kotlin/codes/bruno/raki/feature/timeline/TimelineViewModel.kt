package codes.bruno.raki.feature.timeline

import androidx.lifecycle.ViewModel
import androidx.paging.map
import codes.bruno.raki.core.domain.usecase.FetchTimelineUseCase
import codes.bruno.raki.core.domain.usecase.FormatRelativeDateTimeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import java.time.OffsetDateTime
import javax.inject.Inject

@HiltViewModel
internal class TimelineViewModel @Inject constructor(
    fetchTimelineUseCase: FetchTimelineUseCase,
    formatRelativeDateTimeUseCase: FormatRelativeDateTimeUseCase,
) : ViewModel() {

    val timeline = fetchTimelineUseCase().map { pagingData ->
        pagingData.map {
            TimelineStatusUi(
                id = it.id,
                authorAvatarUrl = it.authorAvatarUrl,
                authorDisplayName = it.authorDisplayName,
                authorAcct = it.authorAcct,
                relativeCreatedAt = formatRelativeDateTimeUseCase(it.createdAt, OffsetDateTime.now()),
                content = it.content,
            )
        }
    }

}

internal data class TimelineStatusUi(
    val id: String,
    val authorAvatarUrl: String,
    val authorDisplayName: String,
    val authorAcct: String,
    val relativeCreatedAt: String,
    val content: String,
)