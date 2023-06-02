package codes.bruno.raki.feature.search

import androidx.compose.material3.Text
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

const val SearchRoute = "search"

fun NavGraphBuilder.searchFeature() {
    composable(SearchRoute) {
        Text(text = "Search route") // TODO
    }
}
