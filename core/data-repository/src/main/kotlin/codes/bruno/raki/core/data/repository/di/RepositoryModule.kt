package codes.bruno.raki.core.data.repository.di

import codes.bruno.raki.core.data.repository.AuthDataRepositoryImpl
import codes.bruno.raki.core.data.repository.MastodonDataRepositoryImpl
import codes.bruno.raki.core.domain.repository.AuthDataRepository
import codes.bruno.raki.core.domain.repository.MastodonDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Binds
    fun AuthDataRepositoryImpl.bindAuthDataRepository(): AuthDataRepository

    @Binds
    fun MastodonDataRepositoryImpl.bindMastodonDataRepository(): MastodonDataRepository

}