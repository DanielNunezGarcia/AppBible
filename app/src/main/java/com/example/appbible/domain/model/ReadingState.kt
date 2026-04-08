package com.example.appbible.domain.model

sealed class ReadingState {
    object Loading : ReadingState()
    data class Success(val reading: DailyReading) : ReadingState()
    data class Error(val message: String) : ReadingState()
}
