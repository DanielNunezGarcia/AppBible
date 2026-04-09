package com.example.appbible.di

import com.example.appbible.data.repository.DailyReadingRepository
import com.example.appbible.domain.usecase.GetDailyReadingUseCase
import com.example.appbible.domain.usecase.GetReadingStreakUseCase
import com.example.appbible.domain.usecase.MarkAsReadUseCase
import com.example.appbible.game.engine.FillVerseEngine
import com.example.appbible.game.engine.MemorizeEngine
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetDailyReadingUseCase(
        repository: DailyReadingRepository
    ): GetDailyReadingUseCase {
        return GetDailyReadingUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideMarkAsReadUseCase(
        repository: DailyReadingRepository
    ): MarkAsReadUseCase {
        return MarkAsReadUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetReadingStreakUseCase(
        repository: DailyReadingRepository
    ): GetReadingStreakUseCase {
        return GetReadingStreakUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideFillVerseEngine(): FillVerseEngine {
        return FillVerseEngine()
    }

    @Provides
    @Singleton
    fun provideMemorizeEngine(): MemorizeEngine {
        return MemorizeEngine()
    }
}