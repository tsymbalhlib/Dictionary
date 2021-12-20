package com.example.android.dictionary.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWords(words: List<DatabaseEntity>)

    @Query("SELECT * FROM words WHERE word LIKE '%' || :word || '%'")
    suspend fun getWords(word: String): List<DatabaseEntity>

    @Query("DELETE FROM words")
    suspend fun deleteWords()
}