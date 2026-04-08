package com.example.appbible.di

import com.example.appbible.data.local.dao.ReadingProgressDao
import com.example.appbible.data.local.dao.VerseDao
import com.example.appbible.data.repository.DailyReadingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideDailyReadingRepository(
        verseDao: VerseDao,
        progressDao: ReadingProgressDao
    ): DailyReadingRepository {
        return DailyReadingRepository(verseDao, progressDao)
    }
}
