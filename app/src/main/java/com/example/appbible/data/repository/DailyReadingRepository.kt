package com.example.appbible.data.repository

import com.example.appbible.data.local.dao.ReadingProgressDao
import com.example.appbible.data.local.dao.VerseDao
import com.example.appbible.data.local.entity.ReadingProgressEntity
import com.example.appbible.data.local.entity.VerseEntity
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DailyReadingRepository @Inject constructor(
    private val verseDao: VerseDao,
    private val progressDao: ReadingProgressDao
) {

    private val dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE

    fun getDailyReading(day: Int, version: String): Flow<List<VerseEntity>> {
        return verseDao.getVersesForDay(day, version)
    }

    fun getThematicReading(theme: String, version: String): Flow<List<VerseEntity>> {
        return verseDao.getThematicVerses(theme, version)
    }

    suspend fun markAsRead(date: String, verseId: Long, version: String) {
        val progress = ReadingProgressEntity(
            date = date,
            verseId = verseId,
            isCompleted = true,
            completedAt = System.currentTimeMillis(),
            version = version
        )
        progressDao.markAsRead(progress)
    }

    fun getProgressByDate(date: String): Flow<ReadingProgressEntity?> {
        return progressDao.getProgressByDate(date)
    }

    suspend fun calculateStreak(): Int {
        val completedDates = progressDao.getCompletedDates()
        if (completedDates.isEmpty()) return 0

        val sortedDates = completedDates
            .map { LocalDate.parse(it, dateFormatter) }
            .sortedDescending()

        val today = LocalDate.now()
        val yesterday = today.minusDays(1)

        val firstDate = sortedDates.first()
        if (firstDate != today && firstDate != yesterday) return 0

        var streak = 1
        for (i in 0 until sortedDates.size - 1) {
            val current = sortedDates[i]
            val previous = sortedDates[i + 1]
            if (ChronoUnit.DAYS.between(previous, current) == 1L) {
                streak++
            } else {
                break
            }
        }

        return streak
    }

    suspend fun getAllThemes(): List<String> {
        return verseDao.getAllThemes()
    }

    suspend fun getTotalCompleted(): Int {
        return progressDao.getTotalCompleted()
    }

    fun getCurrentDateFormatted(): String {
        return LocalDate.now().format(dateFormatter)
    }

    fun getCurrentDayOfYear(): Int {
        return LocalDate.now().dayOfYear
    }

    fun isWeekend(): Boolean {
        val dayOfWeek = LocalDate.now().dayOfWeek.value
        return dayOfWeek == 6 || dayOfWeek == 7
    }

    fun getThematicWeekNumber(): Int {
        val dayOfYear = getCurrentDayOfYear()
        return ((dayOfYear - 1) / 7) % 8
    }

    companion object {
        val THEMATIC_WEEKS = listOf(
            "fe",
            "amor",
            "esperanza",
            "sabiduría",
            "oración",
            "perdón",
            "fortaleza",
            "gratitud"
        )
    }
}
