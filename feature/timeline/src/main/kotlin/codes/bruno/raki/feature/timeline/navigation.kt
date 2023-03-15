package codes.bruno.raki.feature.timeline

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.timelineFeature() {
    composable(route = "timeline") {
        TimelineScreen()
    }
}