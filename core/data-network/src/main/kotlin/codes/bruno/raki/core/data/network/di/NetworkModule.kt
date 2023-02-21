package codes.bruno.raki.core.data.network.di

import codes.bruno.raki.core.data.network.retrofit.DOMAIN_PLACEHOLDER
import codes.bruno.raki.core.data.network.retrofit.InstanceDomainAuthInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    fun provideHttpClient(): OkHttpClient {
        val commonInterceptor = { chain: Interceptor.Chain ->
            val userAgent = "raki/1.0" // TODO: add version name

            chain.proceed(chain.request().newBuilder().header("User-Agent", userAgent).build())
        }

        return OkHttpClient.Builder().apply {
            addInterceptor(commonInterceptor)
            addInterceptor(InstanceDomainAuthInterceptor())
        }.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(httpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://$DOMAIN_PLACEHOLDER") // Retrofit requires a non null base url.
            .client(httpClient).addConverterFactory(MoshiConverterFactory.create()).build()
    }
}
