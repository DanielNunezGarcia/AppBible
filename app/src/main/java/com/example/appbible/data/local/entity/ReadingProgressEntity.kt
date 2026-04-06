package com.example.appbible.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "reading_progress",
    indices = [
        Index(value = ["date"], name = "idx_progress_date"),
        Index(value = ["isCompleted", "date"], name = "idx_progress_streak")
    ]
)
data class ReadingProgressEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val date: String,
    val verseId: Long,
    val isCompleted: Boolean,
    val completedAt: Long? = null,
    val notes: String? = null,
    val version: String
)
