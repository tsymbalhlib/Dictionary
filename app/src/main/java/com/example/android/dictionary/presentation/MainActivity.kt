package com.example.android.dictionary.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.android.dictionary.R
import com.example.android.dictionary.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: DictionaryViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        binding.viewModel = viewModel

        binding.lifecycleOwner = this

        binding.buttonWordDefinitions.setOnClickListener {
            val query = binding.editTextWord.text
            if (query.isNullOrEmpty()) return@setOnClickListener
            viewModel.getDefinitions(query.toString())
        }

        viewModel.showSnackBarEventValue.observe(this, { messageEvent ->
            messageEvent.getContentIfNotHandled()?.let { message ->
                Snackbar.make(
                    binding.root, message, Snackbar.LENGTH_SHORT
                ).show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.clear_database -> {
                viewModel.clearDatabase()
                return true
            }
            else -> false
        }
    }
}