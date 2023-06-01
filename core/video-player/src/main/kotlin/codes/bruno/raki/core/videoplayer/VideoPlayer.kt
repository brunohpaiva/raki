package codes.bruno.raki.core.videoplayer

import android.view.SurfaceView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer

@Composable
fun VideoPlayer(
    modifier: Modifier = Modifier,
    url: String,
) {
    val context = LocalContext.current
    val player = remember(context) {
        ExoPlayer.Builder(context).build()
    }

    LaunchedEffect(player, url) {
        player.setMediaItem(MediaItem.fromUri(url))
        player.playWhenReady = true
        player.prepare()
    }

    var playing by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(false) }

    DisposableEffect(player) {
        val listener = object : Player.Listener {
            override fun onIsPlayingChanged(isPlaying: Boolean) {
                playing = isPlaying
            }

            override fun onIsLoadingChanged(isLoading: Boolean) {
                loading = isLoading
            }
        }

        player.addListener(listener)

        onDispose {
            player.removeListener(listener)
            player.release()
        }
    }

    Box(modifier = modifier) {
        AndroidView(
            factory = { viewContext ->
                SurfaceView(viewContext).also {
                    player.setVideoSurfaceView(it)
                }
            },
            modifier = Modifier.fillMaxSize(),
        )

        VideoControls(
            loading = loading,
            playing = playing,
            onClickPlay = {
                if (playing)
                    player.pause()
                else
                    player.play()
            },
        )
    }
}

@Composable
fun BoxScope.VideoControls(
    loading: Boolean,
    playing: Boolean,
    onClickPlay: () -> Unit,
) {
    if (loading) {
        CircularProgressIndicator(
            modifier = Modifier.align(Alignment.Center),
            color = Color.Black,
        )
    } else {
        IconButton(
            onClick = onClickPlay,
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = Color.Black,
            ),
            modifier = Modifier.align(Alignment.Center),
        ) {
            Icon(
                imageVector = if (playing) {
                    Icons.Filled.Pause
                } else {
                    Icons.Filled.PlayArrow
                },
                contentDescription = stringResource(
                    if (playing) {
                        R.string.pause_button
                    } else {
                        R.string.play_button
                    },
                ),
                modifier = Modifier.size(64.dp),
            )
        }
    }
}