package com.example.appbible.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_scores")
data class ScoreEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val totalPoints: Int,
    val totalXP: Int,
    val gamesPlayed: Int,
    val gamesWon: Int,
    val streakDays: Int,
    val bestStreakDays: Int,
    val versesMemorized: Int,
    val readingsCompleted: Int,
    val level: Int,
    val lastActiveDate: String
)
