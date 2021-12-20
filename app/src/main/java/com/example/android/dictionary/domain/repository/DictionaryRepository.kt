package com.example.android.dictionary.domain.repository

import com.example.android.dictionary.domain.common.Result
import com.example.android.dictionary.domain.entities.Word
import kotlinx.coroutines.flow.Flow

interface DictionaryRepository {

    suspend fun getWordsFromDatabase(word: String): List<Word>

    suspend fun getWordsFromNetwork(word: String): Flow<Result<List<Word>>>

    suspend fun clearDatabase()
}