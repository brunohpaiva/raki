package codes.bruno.raki.feature.timeline

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.pullrefresh.PullRefreshIndicator
import androidx.compose.material3.pullrefresh.pullRefresh
import androidx.compose.material3.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey

@Composable
internal fun TimelineScreen() {
    val viewModel: TimelineViewModel = hiltViewModel()

    val timeline = viewModel.timeline.collectAsLazyPagingItems()

    val refreshing by remember {
        derivedStateOf {
            timeline.loadState.refresh == LoadState.Loading
        }
    }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing,
        onRefresh = timeline::refresh,
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pullRefresh(pullRefreshState),
    ) {
        LazyColumn(
            contentPadding = PaddingValues(vertical = 16.dp),
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
                    TimelineStatus(
                        status = item,
                        modifier = Modifier.padding(horizontal = 16.dp),
                    )
                } else {
                    Text(text = "Loading...")
                }

                if (index < timeline.itemCount - 1) {
                    Divider(Modifier.padding(top = 8.dp))
                }
            }
        }

        PullRefreshIndicator(
            refreshing = refreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
        )
    }
}