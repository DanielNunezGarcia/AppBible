package com.example.appbible.game.model

data class FlashCard(
    val id: Int,
    val reference: String,
    val verseText: String,
    val allWords: List<String>,
    val difficulty: String
)