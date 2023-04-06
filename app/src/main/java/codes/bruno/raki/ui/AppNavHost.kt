package codes.bruno.raki.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import codes.bruno.raki.feature.auth.authFeature
import codes.bruno.raki.feature.timeline.navigateToTimeline
import codes.bruno.raki.feature.timeline.timelineFeature

@Composable
internal fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ) {
        authFeature(
            onFinishAuth = {
                navController.navigateToTimeline()
            },
        )

        timelineFeature()
    }
}