package com.example.appbible.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "verses",
    indices = [
        Index(value = ["readingPlanDay", "version"], name = "idx_verse_day_version"),
        Index(value = ["isThematic", "theme", "version"], name = "idx_verse_thematic")
    ]
)
data class VerseEntity(
    @PrimaryKey val id: Long,
    val book: String,
    val chapter: Int,
    val verseNumber: Int,
    val text: String,
    val reference: String,
    val version: String,
    val readingPlanDay: Int,
    val isThematic: Boolean,
    val theme: String? = null
)
