package codes.bruno.raki.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import codes.bruno.raki.feature.timeline.TimelineRoute
import codes.bruno.raki.feature.timeline.R as timelineR

internal enum class TopLevelDestination(
    val route: String,
    @StringRes val labelTextId: Int,
    val selectedIcon: DestinationIcon,
    val unselectedIcon: DestinationIcon,
) {

    HOME(
        TimelineRoute, // TODO: create separated nav routes
        timelineR.string.home_timeline,
        DestinationIcon.Vector(Icons.Filled.Home),
        DestinationIcon.Vector(Icons.Outlined.Home),
    ),
    LOCAL(
        TimelineRoute, // TODO: create separated nav routes
        timelineR.string.local_timeline,
        DestinationIcon.Vector(Icons.Filled.Person), // TODO: change to People icon
        DestinationIcon.Vector(Icons.Outlined.Person),
    )

}

internal sealed interface DestinationIcon {
    data class Vector(val vector: ImageVector) : DestinationIcon
    data class Resource(@DrawableRes val id: Int) : DestinationIcon
}