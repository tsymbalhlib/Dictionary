package com.example.android.dictionary.domain.entities

data class Meaning(
    val partOfSpeech: String,
    val definitions: List<Definition>
)
