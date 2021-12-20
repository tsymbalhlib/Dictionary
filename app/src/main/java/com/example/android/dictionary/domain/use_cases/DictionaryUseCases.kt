package com.example.android.dictionary.domain.use_cases

data class DictionaryUseCases(
    val getWordsFromDatabase: GetWordsFromDatabase,
    val getWordsFromNetwork: GetWordsFromNetwork,
    val clearDatabase: ClearDatabase
)
