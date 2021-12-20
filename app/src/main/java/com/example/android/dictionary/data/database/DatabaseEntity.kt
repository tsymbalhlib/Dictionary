package com.example.android.dictionary.data.database

import androidx.room.*
import com.example.android.dictionary.domain.entities.Meaning
import com.example.android.dictionary.domain.entities.Word

@Entity(tableName = "words")
data class DatabaseEntity(

    @PrimaryKey
    val word: String,

    @ColumnInfo(name = "phonetic")
    val phonetic: String,

    @ColumnInfo(name = "meanings")
    val meanings: List<Meaning>
)

fun DatabaseEntity.asWord(): Word {
    return Word(
        word = word,
        phonetic = phonetic,
        meanings = meanings
    )
}
