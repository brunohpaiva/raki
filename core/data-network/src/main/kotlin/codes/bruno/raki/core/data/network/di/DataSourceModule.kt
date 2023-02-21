package codes.bruno.raki.core.data.network.di

import codes.bruno.raki.core.data.network.MastodonApiDataSource
import codes.bruno.raki.core.data.network.retrofit.MastodonApiRetrofitDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface DataSourceModule {

    @Singleton
    @Binds
    fun MastodonApiRetrofitDataSource.binds(): MastodonApiDataSource

}