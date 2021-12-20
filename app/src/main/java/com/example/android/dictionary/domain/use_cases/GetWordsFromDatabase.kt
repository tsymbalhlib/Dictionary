package com.example.android.dictionary.domain.use_cases

import com.example.android.dictionary.domain.entities.Word
import com.example.android.dictionary.domain.repository.DictionaryRepository

class GetWordsFromDatabase(
    private val repository: DictionaryRepository
) {
    suspend operator fun invoke(word: String) : List<Word> {
        return repository.getWordsFromDatabase(word)
    }
}