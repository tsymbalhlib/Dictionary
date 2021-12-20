package com.example.android.dictionary.presentation.util

import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.android.dictionary.domain.entities.Word
import kotlin.text.StringBuilder

@BindingAdapter("definitions")
fun setText(textView: TextView, words: List<Word>?) {
    words?.let { words ->
        val stringBuilder = StringBuilder()
        for (word in words) {
            stringBuilder.append("word: ${word.word}\n")
            stringBuilder.append("phonetic: ${word.phonetic}\n")
            stringBuilder.append("meanings:\n\n")
            for (meaning in word.meanings) {
                stringBuilder.append("\tpart of speech: ${meaning.partOfSpeech}\n")
                stringBuilder.append("\tdefinitions:\n")
                for (definition in meaning.definitions) {
                    stringBuilder.append("\t${meaning.definitions.indexOf(definition) + 1}. ${definition.definition}\n")
                }
                stringBuilder.append("\n")
            }
            stringBuilder.append("\n\n")
        }
        textView.text = stringBuilder.toString()
        stringBuilder.clear()
    }
}

@BindingAdapter("progressBarVisibility")
fun setProgressVisibility(progressBar: ProgressBar, status: Boolean?) {
    status?.let { loading ->
        if (loading) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}

@BindingAdapter("textViewVisibility")
fun setTextViewVisibility(textView: TextView, status: Boolean?) {
    status?.let { loading ->
        if (loading) {
            textView.visibility = View.GONE
        } else {
            textView.visibility = View.VISIBLE
        }
    }
}