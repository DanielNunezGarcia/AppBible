package com.example.appbible.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "game_progress",
    indices = [
        Index(value = ["gameType"], name = "idx_game_type"),
        Index(value = ["completedAt"], name = "idx_game_date")
    ]
)
data class GameProgressEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val gameType: String,
    val difficulty: String,
    val score: Int,
    val totalQuestions: Int,
    val correctAnswers: Int,
    val wrongAnswers: Int,
    val completedAt: Long,
    val durationSeconds: Long,
    val livesUsed: Int
)
