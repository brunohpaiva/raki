package codes.bruno.raki.core.data.database.di

import codes.bruno.raki.core.data.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseDaoModule {

    @Provides
    fun timelineDao(appDatabase: AppDatabase) = appDatabase.timelineDao()
}