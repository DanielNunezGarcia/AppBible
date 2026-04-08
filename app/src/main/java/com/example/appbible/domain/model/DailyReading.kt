package com.example.appbible.domain.model

data class DailyReading(
    val verses: List<Verse>,
    val date: String,
    val dayNumber: Int,
    val isThematic: Boolean,
    val isCompleted: Boolean,
    val streak: Int,
    val reflection: String? = null
)
