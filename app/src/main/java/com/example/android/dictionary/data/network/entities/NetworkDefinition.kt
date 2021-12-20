package com.example.android.dictionary.data.network.entities

import com.example.android.dictionary.domain.entities.Definition

data class NetworkDefinition(
    val definition: String = "-",
)

fun NetworkDefinition.asDefinition(): Definition {
    return Definition(
        definition = definition
    )
}