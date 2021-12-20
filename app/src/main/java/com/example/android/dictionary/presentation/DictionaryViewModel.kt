package com.example.android.dictionary.presentation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.dictionary.R
import com.example.android.dictionary.domain.common.Result
import com.example.android.dictionary.domain.entities.Word
import com.example.android.dictionary.domain.use_cases.DictionaryUseCases
import com.example.android.dictionary.presentation.util.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

const val LOG_TAG = "errorLog"

@HiltViewModel
class DictionaryViewModel @Inject constructor(
    private val dictionaryUseCases: DictionaryUseCases
) : ViewModel() {

    private val _words = MutableLiveData<List<Word>?>()
    val words: LiveData<List<Word>?> = _words

    private val _loading = MutableLiveData(false)
    val loading: LiveData<Boolean> = _loading

    private val _showSnackBarEventValue = MutableLiveData<Event<Int>>()
    val showSnackBarEventValue: LiveData<Event<Int>> = _showSnackBarEventValue

    fun getDefinitions(word: String?) = viewModelScope.launch {
        if (word.isNullOrEmpty()) return@launch
        dictionaryUseCases.getWordsFromNetwork.invoke(word).onEach { result ->
            when (result) {
                is Result.Loading -> {
                    _loading.value = true
                }
                is Result.Success -> {
                    _words.value = result.data
                    _loading.value = false
                }
                is Result.Error -> {
                    val words = dictionaryUseCases.getWordsFromDatabase.invoke(word)
                    if (words.isNullOrEmpty()) {
                        _words.value = emptyList()
                        _showSnackBarEventValue.value = Event(R.string.error_data_loading)
                    } else {
                        _words.value = words
                        _showSnackBarEventValue.value = Event(R.string.error_previous_results)
                    }
                    _loading.value = false
                    Log.e(LOG_TAG, result.exception.toString())
                }
            }
        }.launchIn(this)
    }

    fun clearDatabase() = viewModelScope.launch {
        dictionaryUseCases.clearDatabase.invoke()
        _showSnackBarEventValue.value = Event(R.string.all_data_deketed)
    }
}