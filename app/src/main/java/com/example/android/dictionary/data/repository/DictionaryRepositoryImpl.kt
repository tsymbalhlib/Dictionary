package com.example.android.dictionary.data.repository

import com.example.android.dictionary.data.database.DatabaseDao
import com.example.android.dictionary.data.database.asWord
import com.example.android.dictionary.data.network.DictionaryApiService
import com.example.android.dictionary.data.network.entities.asDatabaseEntity
import com.example.android.dictionary.data.network.entities.asWord
import com.example.android.dictionary.domain.common.Result
import com.example.android.dictionary.domain.entities.Word
import com.example.android.dictionary.domain.repository.DictionaryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DictionaryRepositoryImpl(
    private val databaseDao: DatabaseDao,
    private val dictionaryApiService: DictionaryApiService
) : DictionaryRepository {

    override suspend fun getWordsFromDatabase(word: String): List<Word> {
        return databaseDao.getWords(word).map {
            it.asWord()
        }
    }

    override suspend fun getWordsFromNetwork(word: String): Flow<Result<List<Word>>> = flow {
        emit(Result.Loading)
        try {
            val result = dictionaryApiService.getWords(word)
            databaseDao.insertWords(result.map {
                it.asDatabaseEntity()
            })
            emit(Result.Success(result.map {
                it.asWord()
            }))
        } catch (exception: Exception) {
            emit(Result.Error(exception))
        }
    }

    override suspend fun clearDatabase() {
        databaseDao.deleteWords()
    }
}