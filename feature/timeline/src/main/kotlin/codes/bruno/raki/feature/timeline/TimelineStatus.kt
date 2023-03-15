package codes.bruno.raki.feature.timeline

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import codes.bruno.raki.core.domain.model.TimelineStatus
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
internal fun TimelineStatus(
    modifier: Modifier = Modifier,
    status: TimelineStatus,
) {
    Column(modifier = modifier) {
        Row {
            val context = LocalContext.current
            AsyncImage(
                model = ImageRequest.Builder(context)
                    .data(status.authorAvatarUrl)
                    .size(with(LocalDensity.current) { 30.dp.roundToPx() })
                    .build(),
                contentDescription = null,
            )

            Column {
                Text(text = status.authorDisplayName)
            }
        }

        Text(text = status.content)

        Divider(modifier = Modifier.fillMaxWidth())
    }
}