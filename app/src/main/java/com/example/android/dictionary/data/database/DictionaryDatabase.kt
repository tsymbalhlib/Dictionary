package com.example.android.dictionary.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [DatabaseEntity::class], version = 1, exportSchema = false)
@TypeConverters(TypeConverter::class)
abstract class DictionaryDatabase : RoomDatabase() {

    abstract val databaseDao: DatabaseDao
}