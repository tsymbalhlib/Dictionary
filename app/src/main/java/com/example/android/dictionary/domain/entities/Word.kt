package com.example.android.dictionary.domain.entities

data class Word(
    val word: String,
    val phonetic: String,
    val meanings: List<Meaning>
)
