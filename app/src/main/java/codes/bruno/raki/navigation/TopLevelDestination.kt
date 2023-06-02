package codes.bruno.raki.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector
import codes.bruno.raki.feature.search.R as searchR
import codes.bruno.raki.feature.search.SearchRoute
import codes.bruno.raki.feature.timeline.HomeTimelineRoute
import codes.bruno.raki.feature.timeline.LocalTimelineRoute
import codes.bruno.raki.feature.timeline.R as timelineR

internal enum class TopLevelDestination(
    val route: String,
    @StringRes val labelTextId: Int,
    val selectedIcon: DestinationIcon,
    val unselectedIcon: DestinationIcon,
) {

    HOME(
        HomeTimelineRoute,
        timelineR.string.home_timeline,
        DestinationIcon.Vector(Icons.Filled.Home),
        DestinationIcon.Vector(Icons.Outlined.Home),
    ),
    LOCAL(
        LocalTimelineRoute,
        timelineR.string.local_timeline,
        DestinationIcon.Vector(Icons.Filled.Person), // TODO: change to People icon
        DestinationIcon.Vector(Icons.Outlined.Person),
    ),
    SEARCH(
        SearchRoute,
        searchR.string.search,
        DestinationIcon.Vector(Icons.Filled.Search),
        DestinationIcon.Vector(Icons.Outlined.Search),
    ),

}

internal sealed interface DestinationIcon {
    data class Vector(val vector: ImageVector) : DestinationIcon
    data class Resource(@DrawableRes val id: Int) : DestinationIcon
}