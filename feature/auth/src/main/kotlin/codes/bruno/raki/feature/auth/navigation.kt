package codes.bruno.raki.feature.auth

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink

fun NavGraphBuilder.authFeature(onFinishAuth: () -> Unit) {
    composable(
        route = "login",
        deepLinks = listOf(
            navDeepLink { uriPattern = "raki://oauth2-callback?code={code}" },
        ),
    ) {
        LoginScreen(
            returnedCode = it.arguments?.getString("code"),
            onFinishAuth = onFinishAuth,
        )
    }
}