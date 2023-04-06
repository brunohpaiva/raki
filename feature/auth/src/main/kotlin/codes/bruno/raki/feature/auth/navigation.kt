package codes.bruno.raki.feature.auth

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navDeepLink

const val LoginRoute = "login"

fun NavGraphBuilder.authFeature(onFinishAuth: () -> Unit) {
    composable(
        route = LoginRoute,
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

fun NavController.navigateToLogin(builder: NavOptionsBuilder.() -> Unit) {
    navigate(LoginRoute, builder)
}