package com.example.android.dictionary.data.network.entities

import com.example.android.dictionary.data.database.DatabaseEntity
import com.example.android.dictionary.domain.entities.Word


data class NetworkWord(
    val meanings: List<NetworkMeaning>,
    val phonetic: String = "-",
    val word: String = "-"
)

fun NetworkWord.asDatabaseEntity(): DatabaseEntity {
    return DatabaseEntity(
        word = word,
        phonetic = phonetic,
        meanings = meanings.map {
            it.asMeaning()
        }
    )
}

fun NetworkWord.asWord(): Word {
    return Word(
        word = word,
        phonetic = phonetic,
        meanings = meanings.map {
            it.asMeaning()
        }
    )
}