package com.example.appbible.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.appbible.data.local.entity.GameProgressEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface GameProgressDao {

    @Insert
    suspend fun insertGameProgress(progress: GameProgressEntity)

    @Query("SELECT * FROM game_progress WHERE gameType = :gameType ORDER BY completedAt DESC LIMIT :limit")
    fun getGameProgressByType(gameType: String, limit: Int = 20): Flow<List<GameProgressEntity>>

    @Query("SELECT * FROM game_progress WHERE gameType = :gameType ORDER BY score DESC LIMIT 1")
    suspend fun getBestScoreByType(gameType: String): GameProgressEntity?

    @Query("SELECT COUNT(*) FROM game_progress")
    suspend fun getTotalGamesPlayed(): Int

    @Query("SELECT * FROM game_progress WHERE gameType = :gameType AND difficulty = :difficulty ORDER BY completedAt DESC")
    fun getGamesByDifficulty(gameType: String, difficulty: String): Flow<List<GameProgressEntity>>
}
