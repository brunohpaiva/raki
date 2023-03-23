package codes.bruno.raki.core.data.network.di

import android.content.Context
import android.os.Build
import codes.bruno.raki.core.data.network.model.GifMediaAttachment
import codes.bruno.raki.core.data.network.model.ImageMediaAttachment
import codes.bruno.raki.core.data.network.model.MediaAttachment
import codes.bruno.raki.core.data.network.model.VideoMediaAttachment
import codes.bruno.raki.core.data.network.retrofit.DOMAIN_PLACEHOLDER
import codes.bruno.raki.core.data.network.retrofit.InstanceDomainAuthInterceptor
import codes.bruno.raki.core.data.network.retrofit.OffsetDateTimeAdapter
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient(
        instanceDomainAuthInterceptor: InstanceDomainAuthInterceptor,
    ): OkHttpClient {
        val commonInterceptor = { chain: Interceptor.Chain ->
            val userAgent = "raki/1.0" // TODO: add version name

            chain.proceed(chain.request().newBuilder().header("User-Agent", userAgent).build())
        }

        return OkHttpClient.Builder().apply {
            addInterceptor(commonInterceptor)
            addInterceptor(instanceDomainAuthInterceptor)
        }.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://$DOMAIN_PLACEHOLDER") // Retrofit requires a non null base url.
            .client(httpClient).addConverterFactory(
                MoshiConverterFactory.create(
                    Moshi.Builder()
                        .add(
                            PolymorphicJsonAdapterFactory
                                .of(MediaAttachment::class.java, "type")
                                .withSubtype(ImageMediaAttachment::class.java, "image")
                                .withSubtype(VideoMediaAttachment::class.java, "video")
                                .withSubtype(GifMediaAttachment::class.java, "gifv")
                        )
                        .add(OffsetDateTimeAdapter)
                        .build(),
                ),
            ).build()
    }

    @Singleton
    @Provides
    fun provideImageLoader(
        @ApplicationContext context: Context,
        okHttpClient: OkHttpClient,
    ): ImageLoader {
        return ImageLoader.Builder(context)
            .okHttpClient(okHttpClient)
            .components {
                add(
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        ImageDecoderDecoder.Factory()
                    } else {
                        GifDecoder.Factory()
                    }
                )
            }
            .build()
    }
}
