package codes.bruno.raki.feature.timeline

import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val HomeTimelineRoute = "homeTimeline"
const val LocalTimelineRoute = "localTimeline"

fun NavGraphBuilder.timelineFeature() {
    composable(HomeTimelineRoute) {
        TimelineScreen()
    }

    composable(LocalTimelineRoute) {
        Text(text = "Local timeline route") // TODO
    }
}

fun NavController.navigateToHomeTimeline() {
    navigate(HomeTimelineRoute)
}