package codes.bruno.raki.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import codes.bruno.raki.navigation.AppNavHost
import codes.bruno.raki.navigation.DestinationIcon
import codes.bruno.raki.navigation.TopLevelDestination

@Composable
internal fun App(
    windowSizeClass: WindowSizeClass,
    isLoggedIn: Boolean,
    state: AppState = rememberAppState(
        windowSizeClass = windowSizeClass,
        isLoggedIn = isLoggedIn,
    ),
) {
    val onNavigateToDestination = { destination: TopLevelDestination ->
        state.navController.navigate(destination.route) {
            launchSingleTop = true
            restoreState = true
            popUpTo(TopLevelDestination.HOME.route) {
                saveState = true
            }
        }
    }


    Scaffold(
        bottomBar = {
            if (state.shouldShowBottomNav) {
                BottomNav(
                    destinations = AppState.topLevelDestinations,
                    onNavigateToDestination = onNavigateToDestination,
                    currentDestination = state.currentDestination,
                )
            }
        },
    ) { contentPadding ->
        Row(modifier = Modifier.fillMaxSize()) {
            if (state.shouldShowRailNav) {
                RailNav(
                    destinations = AppState.topLevelDestinations,
                    onNavigateToDestination = onNavigateToDestination,
                    currentDestination = state.currentDestination,
                )
            }

            AppNavHost(
                modifier = Modifier.padding(contentPadding),
                navController = state.navController,
                startDestination = state.startDestination,
            )
        }
    }
}

@Composable
private fun BottomNav(
    modifier: Modifier = Modifier,
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
) {
    NavigationBar(modifier) {
        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)

            NavigationBarItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    val currentIcon = if (selected) {
                        destination.selectedIcon
                    } else {
                        destination.unselectedIcon
                    }

                    DestinationIcon(currentIcon)
                },
                label = { Text(text = stringResource(destination.labelTextId)) },
            )
        }
    }
}

@Composable
private fun RailNav(
    modifier: Modifier = Modifier,
    destinations: List<TopLevelDestination>,
    onNavigateToDestination: (TopLevelDestination) -> Unit,
    currentDestination: NavDestination?,
) {
    NavigationRail(modifier) {
        // Wrapping the items with Spacers to make them vertically centered.
        Spacer(Modifier.weight(1f))

        destinations.forEach { destination ->
            val selected = currentDestination.isTopLevelDestinationInHierarchy(destination)

            NavigationRailItem(
                selected = selected,
                onClick = { onNavigateToDestination(destination) },
                icon = {
                    val currentIcon = if (selected) {
                        destination.selectedIcon
                    } else {
                        destination.unselectedIcon
                    }

                    DestinationIcon(currentIcon)
                },
                label = { Text(text = stringResource(destination.labelTextId)) },
            )
        }

        Spacer(Modifier.weight(1f))
    }
}

@Composable
private fun DestinationIcon(icon: DestinationIcon) {
    when (icon) {
        is DestinationIcon.Vector -> Icon(imageVector = icon.vector, contentDescription = null)
        is DestinationIcon.Resource -> Icon(
            painter = painterResource(id = icon.id),
            contentDescription = null,
        )
    }
}


private fun NavDestination?.isTopLevelDestinationInHierarchy(destination: TopLevelDestination) =
    this?.hierarchy?.any {
        it.route?.contains(destination.route, true) ?: false
    } ?: false