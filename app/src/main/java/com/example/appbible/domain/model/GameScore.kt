package com.example.appbible.domain.model

data class GameScore(
    val totalPoints: Int,
    val totalXP: Int,
    val gamesPlayed: Int,
    val gamesWon: Int,
    val streakDays: Int,
    val bestStreakDays: Int,
    val versesMemorized: Int,
    val readingsCompleted: Int,
    val level: Int
)
