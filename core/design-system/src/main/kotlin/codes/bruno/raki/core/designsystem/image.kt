package codes.bruno.raki.core.designsystem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import coil.ImageLoader
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.components.SingletonComponent

@Composable
fun rememberImageLoader(): ImageLoader {
    val context = LocalContext.current.applicationContext
    return remember(context) {
        EntryPointAccessors.fromApplication(context, ImageLoaderEntryPoint::class.java)
            .imageLoader()
    }
}

@EntryPoint
@InstallIn(SingletonComponent::class)
internal interface ImageLoaderEntryPoint {
    fun imageLoader(): ImageLoader
}