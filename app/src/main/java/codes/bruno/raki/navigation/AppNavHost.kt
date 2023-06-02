package codes.bruno.raki.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import codes.bruno.raki.feature.auth.authFeature
import codes.bruno.raki.feature.search.searchFeature
import codes.bruno.raki.feature.timeline.navigateToHomeTimeline
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
                navController.navigateToHomeTimeline()
            },
        )

        timelineFeature()
        searchFeature()
    }
}