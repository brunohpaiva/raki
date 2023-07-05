package codes.bruno.raki.feature.timeline

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Reply
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
    status: TimelineStatusUi,
    onClickReply: () -> Unit,
    onClickBoost: () -> Unit,
    onClickFavourite: () -> Unit,
    onClickBookmark: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier,
    ) {
        if (status.rebloggerDisplayName != null) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Icon(
                    imageVector = Icons.Filled.Repeat,
                    contentDescription = null,
                )

                Text(
                    text = status.rebloggerDisplayName,
                    modifier = Modifier.align(Alignment.CenterVertically),
                )
            }
        }

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

        Interactions(
            status = status,
            onClickReply = onClickReply,
            onClickBoost = onClickBoost,
            onClickFavourite = onClickFavourite,
            onClickBookmark = onClickBookmark,
        )
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

@Composable
private fun Interactions(
    status: TimelineStatusUi,
    onClickReply: () -> Unit,
    onClickBoost: () -> Unit,
    onClickFavourite: () -> Unit,
    onClickBookmark: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier.fillMaxWidth(),
    ) {
        // TODO: add content descriptions

        IconButton(onClick = onClickReply, enabled = false) {
            Icon(imageVector = Icons.Filled.Reply, contentDescription = null)
        }

        IconButton(onClick = onClickBoost, enabled = false) {
            Icon(imageVector = Icons.Filled.Repeat, contentDescription = null)
        }

        IconButton(onClick = onClickFavourite) {
            Icon(
                imageVector = if (!status.favourited)
                    Icons.Filled.StarBorder
                else
                    Icons.Filled.Star,
                contentDescription = null,
            )
        }

        IconButton(onClick = onClickBookmark, enabled = false) {
            Icon(imageVector = Icons.Filled.BookmarkBorder, contentDescription = null)
        }
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
                rebloggerAvatarUrl = null,
                rebloggerDisplayName = null,
                rebloggerAcct = null,
                relativeCreatedAt = "38m",
                content = buildAnnotatedString {
                    append("This is a #mastodon status example")
                },
                mediaAttachments = emptyList(),
                favourited = false,
            ),
            onClickReply = {},
            onClickBoost = {},
            onClickFavourite = {},
            onClickBookmark = {},
        )
    }
}