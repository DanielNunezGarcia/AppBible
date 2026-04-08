package com.example.appbible.domain.model

data class Verse(
    val id: Long,
    val book: String,
    val chapter: Int,
    val verseNumber: Int,
    val text: String,
    val reference: String,
    val version: String,
    val theme: String? = null
)
