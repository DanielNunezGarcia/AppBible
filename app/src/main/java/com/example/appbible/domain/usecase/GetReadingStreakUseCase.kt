package com.example.appbible.domain.usecase

import com.example.appbible.data.repository.DailyReadingRepository

class GetReadingStreakUseCase(
    private val repository: DailyReadingRepository
) {

    suspend operator fun invoke(): Int {
        return repository.calculateStreak()
    }
}
