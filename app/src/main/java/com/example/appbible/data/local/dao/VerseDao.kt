package com.example.appbible.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appbible.data.local.entity.VerseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VerseDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertVerse(verse: VerseEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertVerses(verses: List<VerseEntity>)

    @Query("SELECT * FROM verses WHERE readingPlanDay = :day AND version = :version ORDER BY chapter, verseNumber")
    fun getVersesForDay(day: Int, version: String = "RVR1960"): Flow<List<VerseEntity>>

    @Query("SELECT * FROM verses WHERE isThematic = 1 AND theme = :theme AND version = :version ORDER BY chapter, verseNumber")
    fun getThematicVerses(theme: String, version: String = "RVR1960"): Flow<List<VerseEntity>>

    @Query("SELECT * FROM verses WHERE id = :id")
    suspend fun getVerseById(id: Long): VerseEntity?

    @Query("SELECT DISTINCT theme FROM verses WHERE isThematic = 1 AND theme IS NOT NULL")
    suspend fun getAllThemes(): List<String>

    @Query("SELECT COUNT(*) FROM verses")
    suspend fun getTotalVerses(): Int
}
