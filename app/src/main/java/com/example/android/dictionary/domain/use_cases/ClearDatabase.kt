package com.example.android.dictionary.domain.use_cases

import com.example.android.dictionary.domain.repository.DictionaryRepository

class ClearDatabase(
    private val repository: DictionaryRepository
) {
    suspend operator fun invoke() {
        repository.clearDatabase()
    }
}