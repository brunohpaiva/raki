package codes.bruno.raki.data.remote.di

import codes.bruno.raki.data.remote.api.MastodonService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ServiceModule {

    @Provides
    @Singleton
    fun provideMastodonService(retrofit: Retrofit): MastodonService = retrofit.create()

}
