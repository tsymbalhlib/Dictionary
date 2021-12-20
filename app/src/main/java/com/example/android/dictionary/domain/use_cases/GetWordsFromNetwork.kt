package com.example.android.dictionary.domain.use_cases

import com.example.android.dictionary.domain.common.Result
import com.example.android.dictionary.domain.entities.Word
import com.example.android.dictionary.domain.repository.DictionaryRepository
import kotlinx.coroutines.flow.Flow

class GetWordsFromNetwork(
    private val repository: DictionaryRepository
) {
    suspend operator fun invoke(word: String) : Flow<Result<List<Word>>> {
        return repository.getWordsFromNetwork(word)
    }
}