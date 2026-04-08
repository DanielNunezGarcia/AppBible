package com.example.appbible.domain.model

data class Achievement(
    val id: Long,
    val name: String,
    val description: String,
    val icon: String,
    val category: String,
    val requirement: Int,
    val xpReward: Int,
    val isUnlocked: Boolean,
    val unlockedAt: Long? = null
)
