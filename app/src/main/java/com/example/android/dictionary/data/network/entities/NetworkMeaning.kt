package com.example.android.dictionary.data.network.entities

import com.example.android.dictionary.domain.entities.Meaning

data class NetworkMeaning(
    val partOfSpeech: String = "-",
    val definitions: List<NetworkDefinition>
)

fun NetworkMeaning.asMeaning(): Meaning {
    return Meaning(
        partOfSpeech = partOfSpeech,
        definitions = definitions.map {
            it.asDefinition()
        }
    )
}