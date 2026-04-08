package com.example.appbible.di

import android.content.Context
import androidx.room.Room
import com.example.appbible.data.local.AppDatabase
import com.example.appbible.data.local.dao.AchievementDao
import com.example.appbible.data.local.dao.GameProgressDao
import com.example.appbible.data.local.dao.ReadingProgressDao
import com.example.appbible.data.local.dao.ScoreDao
import com.example.appbible.data.local.dao.VerseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideVerseDao(database: AppDatabase): VerseDao {
        return database.verseDao()
    }

    @Provides
    @Singleton
    fun provideReadingProgressDao(database: AppDatabase): ReadingProgressDao {
        return database.readingProgressDao()
    }

    @Provides
    @Singleton
    fun provideGameProgressDao(database: AppDatabase): GameProgressDao {
        return database.gameProgressDao()
    }

    @Provides
    @Singleton
    fun provideAchievementDao(database: AppDatabase): AchievementDao {
        return database.achievementDao()
    }

    @Provides
    @Singleton
    fun provideScoreDao(database: AppDatabase): ScoreDao {
        return database.scoreDao()
    }
}
