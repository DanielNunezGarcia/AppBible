package com.example.appbible.game.model

data class FlashCard(
    val id: Int,
    val verseText: String,
    val reference: String,
    val hiddenWords: List<Int>, // Índices de palabras ocultas
    val allWords: List<String>,
    val difficulty: String
)
