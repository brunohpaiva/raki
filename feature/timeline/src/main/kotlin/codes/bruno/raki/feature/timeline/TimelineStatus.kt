package codes.bruno.raki.feature.timeline

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import codes.bruno.raki.core.designsystem.rememberImageLoader
import codes.bruno.raki.core.designsystem.theme.RakiTheme
import codes.bruno.raki.core.domain.model.MediaAttachmentType
import codes.bruno.raki.core.videoplayer.VideoPlayer
import coil.compose.AsyncImage
import coil.request.ImageRequest
import codes.bruno.raki.core.designsystem.R as DesignSystemR

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
            AccountAvatar(
                url = status.authorAvatarUrl,
                modifier = Modifier.size(50.dp),
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

        ClickableText(
            text = status.content,
            onClick = {
                // TODO: handle URL click
            },
            style = MaterialTheme.typography.bodyMedium.copy(
                color = LocalContentColor.current,
            ),
        )

        MediaAttachments(attachments = status.mediaAttachments)
    }
}

@Composable
private fun AccountAvatar(url: String, modifier: Modifier = Modifier) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .size(with(LocalDensity.current) { 50.dp.roundToPx() })
            .build(),
        placeholder = painterResource(DesignSystemR.drawable.missing_user_avatar),
        contentDescription = stringResource(R.string.user_avatar),
        imageLoader = rememberImageLoader(),
        modifier = modifier.clip(RoundedCornerShape(8.dp)),
    )
}

private const val MEDIA_ASPECT_RATIO = 16F / 9F

@Composable
private fun MediaAttachments(attachments: List<StatusMediaAttachmentUi>) {
    // TODO: change to a LazyRow
    Row {
        attachments.forEach { attachment ->
            when (attachment.type) {
                MediaAttachmentType.IMAGE -> ImageAttachment(
                    attachment = attachment,
                    modifier = Modifier.aspectRatio(MEDIA_ASPECT_RATIO),
                )

                MediaAttachmentType.GIFV -> if (attachment.url.endsWith(".gif")) {
                    GifAttachment(
                        attachment = attachment,
                        modifier = Modifier.aspectRatio(MEDIA_ASPECT_RATIO),
                    )
                } else {
                    VideoAttachment(
                        attachment = attachment,
                        modifier = Modifier.aspectRatio(MEDIA_ASPECT_RATIO),
                    )
                }

                MediaAttachmentType.VIDEO -> VideoAttachment(
                    attachment = attachment,
                    modifier = Modifier.aspectRatio(MEDIA_ASPECT_RATIO),
                )

                else -> Text(text = "ATTACHMENT ${attachment.type}")
            }
        }
    }
}

// TODO: merge ImageAttachment and GifAttachment into one single composable,
//  since they are quite the same

@Composable
private fun ImageAttachment(
    attachment: StatusMediaAttachmentUi,
    modifier: Modifier = Modifier,
) {
    // TODO: implement clicking on image to view original
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(attachment.previewUrl)
            .build(),
        contentDescription = attachment.description
            ?: stringResource(R.string.status_media_attachment),
        contentScale = ContentScale.Crop,
        alignment = Alignment.Center,
        imageLoader = rememberImageLoader(),
        modifier = modifier,
    )
}

@Composable
private fun GifAttachment(
    attachment: StatusMediaAttachmentUi,
    modifier: Modifier = Modifier,
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(attachment.url)
            .build(),
        contentDescription = attachment.description
            ?: stringResource(R.string.status_media_attachment),
        contentScale = ContentScale.Crop,
        alignment = Alignment.Center,
        imageLoader = rememberImageLoader(),
        modifier = modifier,
    )
}

@Composable
private fun VideoAttachment(
    attachment: StatusMediaAttachmentUi,
    modifier: Modifier = Modifier,
) {
    VideoPlayer(
        url = attachment.url,
        modifier = modifier,
    )
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
                content = buildAnnotatedString {
                    append("This is a #mastodon status example")
                },
                mediaAttachments = emptyList(),
            )
        )
    }
}