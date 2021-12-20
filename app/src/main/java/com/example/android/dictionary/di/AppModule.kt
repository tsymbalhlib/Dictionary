package com.example.android.dictionary.di

import android.content.Context
import androidx.room.Room
import com.example.android.dictionary.data.database.DatabaseDao
import com.example.android.dictionary.data.database.DictionaryDatabase
import com.example.android.dictionary.data.database.TypeConverter
import com.example.android.dictionary.data.network.DictionaryApiService
import com.example.android.dictionary.data.repository.DictionaryRepositoryImpl
import com.example.android.dictionary.domain.repository.DictionaryRepository
import com.example.android.dictionary.domain.use_cases.ClearDatabase
import com.example.android.dictionary.domain.use_cases.DictionaryUseCases
import com.example.android.dictionary.domain.use_cases.GetWordsFromDatabase
import com.example.android.dictionary.domain.use_cases.GetWordsFromNetwork
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .baseUrl("https://api.dictionaryapi.dev/")
            .build()
    }

    @Singleton
    @Provides
    fun provideDictionaryApiService(retrofit: Retrofit): DictionaryApiService {
        return retrofit.create(DictionaryApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideTypeConverter(moshi: Moshi): TypeConverter {
        return TypeConverter(moshi)
    }

    @Provides
    @Singleton
    fun provideDictionaryDatabase(
        @ApplicationContext appContext: Context,
        typeConverter: TypeConverter
    ): DictionaryDatabase {
        return Room.databaseBuilder(
            appContext.applicationContext,
            DictionaryDatabase::class.java,
            "dictionary_database"
        )
            .addTypeConverter(typeConverter)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideDatabaseDao(database: DictionaryDatabase): DatabaseDao {
        return database.databaseDao
    }

    @Provides
    @Singleton
    fun provideDictionaryRepository(
        databaseDao: DatabaseDao,
        dictionaryApiService: DictionaryApiService
    ): DictionaryRepository {
        return DictionaryRepositoryImpl(databaseDao, dictionaryApiService)
    }

    @Provides
    @Singleton
    fun provideDictionaryUseCases(repository: DictionaryRepository): DictionaryUseCases {
        return DictionaryUseCases(
            getWordsFromDatabase = GetWordsFromDatabase(repository),
            getWordsFromNetwork = GetWordsFromNetwork(repository),
            clearDatabase = ClearDatabase(repository)
        )
    }
}