package com.example.appbible.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appbible.data.local.entity.ReadingProgressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReadingProgressDao {

    @Query("SELECT * FROM reading_progress WHERE date = :date LIMIT 1")
    fun getProgressByDate(date: String): Flow<ReadingProgressEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun markAsRead(progress: ReadingProgressEntity)

    @Query("SELECT COUNT(*) FROM reading_progress WHERE isCompleted = 1")
    suspend fun getTotalCompleted(): Int

    @Query("SELECT date FROM reading_progress WHERE isCompleted = 1 ORDER BY date DESC")
    suspend fun getCompletedDates(): List<String>
}
