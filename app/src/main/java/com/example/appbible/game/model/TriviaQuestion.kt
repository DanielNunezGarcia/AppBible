package com.example.appbible.game.model

data class TriviaQuestion(
    val id: Int,
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val difficulty: String, // "facil", "medio", "dificil"
    val category: String,
    val reference: String,
    val explanation: String
)
