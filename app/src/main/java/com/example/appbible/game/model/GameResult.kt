package com.example.appbible.game.model

data class GameResult(
    val gameType: String,
    val score: Int,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val wrongAnswers: Int,
    val xpEarned: Int,
    val pointsEarned: Int,
    val durationSeconds: Long,
    val newAchievements: List<com.example.appbible.domain.model.Achievement> = emptyList()
)
