package codes.bruno.raki

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import codes.bruno.raki.feature.auth.authFeature
import codes.bruno.raki.feature.timeline.timelineFeature

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun App() {
    val navController = rememberNavController()

    Scaffold(
        modifier = Modifier.consumedWindowInsets(WindowInsets.safeDrawing),
    ) {
        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier.padding(it),
        ) {
            authFeature(
                onFinishAuth = {
                    navController.navigate("timeline")
                },
            )

            timelineFeature()
        }
    }
}