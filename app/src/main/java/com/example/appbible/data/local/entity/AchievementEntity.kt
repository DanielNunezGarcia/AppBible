package com.example.appbible.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "achievements",
    indices = [
        Index(value = ["category"], name = "idx_achievement_category"),
        Index(value = ["isUnlocked"], name = "idx_achievement_unlocked")
    ]
)
data class AchievementEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val description: String,
    val icon: String,
    val category: String,
    val requirement: Int,
    val xpReward: Int,
    val isUnlocked: Boolean,
    val unlockedAt: Long? = null
)
