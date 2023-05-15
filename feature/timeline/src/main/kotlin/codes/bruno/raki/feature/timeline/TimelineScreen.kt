package codes.bruno.raki.feature.timeline

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey

@Composable
internal fun TimelineScreen() {
    val viewModel: TimelineViewModel = hiltViewModel()

    val timeline = viewModel.timeline.collectAsLazyPagingItems()

    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            count = timeline.itemCount,
            key = timeline.itemKey { it.id },
            contentType = timeline.itemContentType {
                1 // TimelineStatus
            },
        ) { index ->
            val item = timeline[index]
            if (item != null) {
                TimelineStatus(status = item)
            } else {
                Text(text = "Loading...")
            }
        }
    }
}