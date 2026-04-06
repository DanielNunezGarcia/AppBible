package com.example.appbible.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appbible.data.local.entity.ScoreEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ScoreDao {

    @Query("SELECT * FROM user_scores LIMIT 1")
    fun getUserScore(): Flow<ScoreEntity?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun initializeScore(score: ScoreEntity)

    @Query("UPDATE user_scores SET totalPoints = totalPoints + :points")
    suspend fun addPoints(points: Int)

    @Query("UPDATE user_scores SET totalXP = totalXP + :xp")
    suspend fun addXP(xp: Int)

    @Query("UPDATE user_scores SET gamesPlayed = gamesPlayed + 1")
    suspend fun incrementGamesPlayed()

    @Query("UPDATE user_scores SET gamesWon = gamesWon + 1")
    suspend fun incrementGamesWon()

    @Query("UPDATE user_scores SET streakDays = :days")
    suspend fun updateStreak(days: Int)

    @Query("UPDATE user_scores SET bestStreakDays = :days WHERE :days > bestStreakDays")
    suspend fun updateBestStreak(days: Int)

    @Query("UPDATE user_scores SET versesMemorized = versesMemorized + 1")
    suspend fun incrementVersesMemorized()

    @Query("UPDATE user_scores SET readingsCompleted = readingsCompleted + 1")
    suspend fun incrementReadingsCompleted()

    @Query("UPDATE user_scores SET level = :level")
    suspend fun updateLevel(level: Int)

    @Query("UPDATE user_scores SET lastActiveDate = :date")
    suspend fun updateLastActiveDate(date: String)
}
