package codes.bruno.raki.feature.timeline

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val TimelineRoute = "timeline"

fun NavGraphBuilder.timelineFeature() {
    composable(TimelineRoute) {
        TimelineScreen()
    }
}

fun NavController.navigateToTimeline() {
    navigate(TimelineRoute)
}