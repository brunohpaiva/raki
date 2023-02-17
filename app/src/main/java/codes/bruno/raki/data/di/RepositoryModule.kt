package codes.bruno.raki.data.di

import codes.bruno.raki.data.repository.AuthDataRepositoryImpl
import codes.bruno.raki.domain.repository.AuthDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Binds
    fun AuthDataRepositoryImpl.binds(): AuthDataRepository

}