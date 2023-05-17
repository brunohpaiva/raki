package codes.bruno.raki.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import codes.bruno.raki.feature.auth.LoginRoute
import codes.bruno.raki.feature.timeline.TimelineRoute

@Composable
internal fun App(
    // TODO: implement UI for different form factors
    @Suppress("UNUSED_PARAMETER") windowSizeClass: WindowSizeClass,
    isLoggedIn: Boolean,
) {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.consumeWindowInsets(WindowInsets.safeDrawing),
        bottomBar = {

        },
    ) {
        val startDestination = remember(isLoggedIn) {
            if (isLoggedIn) {
                TimelineRoute
            } else {
                LoginRoute
            }
        }

        AppNavHost(
            modifier = Modifier.padding(it),
            navController = navController,
            startDestination = startDestination,
        )
    }
}