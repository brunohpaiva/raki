package codes.bruno.raki.feature.timeline

import androidx.lifecycle.ViewModel
import codes.bruno.raki.core.domain.usecase.FetchTimelineUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class TimelineViewModel @Inject constructor(
    fetchTimelineUseCase: FetchTimelineUseCase,
) : ViewModel() {

    val timeline = fetchTimelineUseCase()

}