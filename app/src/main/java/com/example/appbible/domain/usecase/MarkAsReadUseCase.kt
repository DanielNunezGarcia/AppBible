package com.example.appbible.domain.usecase

import com.example.appbible.data.repository.DailyReadingRepository

class MarkAsReadUseCase(
    private val repository: DailyReadingRepository
) {

    suspend operator fun invoke(verseId: Long, version: String = "RVR1960") {
        val date = getCurrentDateFormatted()
        repository.markAsRead(date, verseId, version)
    }

    private fun getCurrentDateFormatted(): String {
        return java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ISO_LOCAL_DATE)
    }
}
