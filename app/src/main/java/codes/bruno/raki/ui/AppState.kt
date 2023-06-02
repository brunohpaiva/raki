package codes.bruno.raki.ui

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import codes.bruno.raki.feature.auth.LoginRoute
import codes.bruno.raki.feature.timeline.HomeTimelineRoute
import codes.bruno.raki.navigation.TopLevelDestination

@Stable
internal class AppState(
    private val windowSizeClass: WindowSizeClass,
    private val isLoggedIn: Boolean,
    val navController: NavHostController,
) {

    val startDestination: String
        get() = if (isLoggedIn) {
            HomeTimelineRoute
        } else {
            LoginRoute
        }

    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    val shouldShowBottomNav: Boolean
        get() = isLoggedIn && windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact

    val shouldShowRailNav: Boolean
        get() = isLoggedIn && windowSizeClass.widthSizeClass > WindowWidthSizeClass.Compact

    companion object {
        val topLevelDestinations: List<TopLevelDestination> = TopLevelDestination.values().asList()
    }

}

@Composable
internal fun rememberAppState(
    windowSizeClass: WindowSizeClass,
    isLoggedIn: Boolean,
    navController: NavHostController = rememberNavController(),
): AppState {
    return remember(
        windowSizeClass,
        isLoggedIn,
        navController,
    ) {
        AppState(windowSizeClass, isLoggedIn, navController)
    }
}