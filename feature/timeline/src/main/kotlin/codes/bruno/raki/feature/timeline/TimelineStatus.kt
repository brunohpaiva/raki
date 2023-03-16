package codes.bruno.raki.feature.timeline

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import codes.bruno.raki.core.designsystem.R
import codes.bruno.raki.core.designsystem.theme.RakiTheme
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
internal fun TimelineStatus(
    modifier: Modifier = Modifier,
    status: TimelineStatusUi,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(status.authorAvatarUrl)
                    .size(with(LocalDensity.current) { 50.dp.roundToPx() })
                    .build(),
                placeholder = painterResource(R.drawable.missing_user_avatar),
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .clip(RoundedCornerShape(8.dp)),
            )

            Row(modifier = Modifier.padding(vertical = 4.dp)) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = status.authorDisplayName)
                    Text(
                        text = "@${status.authorAcct}",
                        color = LocalContentColor.current.copy(alpha = 0.5f),
                    )
                }

                Text(text = status.relativeCreatedAt)
            }
        }

        Text(text = status.content)
    }
}

@Preview
@Composable
private fun TimelineStatusPreview() {
    RakiTheme(darkTheme = true) {
        TimelineStatus(
            status = TimelineStatusUi(
                id = "1234",
                authorAvatarUrl = "",
                authorDisplayName = "Bruno Henrique Paiva",
                authorAcct = "bruno@androiddev.social",
                relativeCreatedAt = "38m",
                content = "content",
            )
        )
    }
}