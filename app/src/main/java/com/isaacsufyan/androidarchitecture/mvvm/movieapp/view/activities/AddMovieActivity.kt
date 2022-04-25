package com.isaacsufyan.androidarchitecture.mvvm.movieapp.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.R
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.action
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.data.model.Movie
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.databinding.ActivityAddBinding
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.snack
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.viewmodel.AddViewModel

class AddMovieActivity : BaseActivity() {

    private lateinit var binding: ActivityAddBinding

    private val viewModel by lazy {
        ViewModelProvider(this)[AddViewModel::class.java]
    }

    override fun getToolbarInstance(): Toolbar {
        return binding.toolbarView.toolbarToolbarView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageButton.setOnClickListener {
            searchMovieClicked()
        }

        binding.addMovieButton.setOnClickListener {
            addMovieClicked()
        }
    }

    private fun addMovieClicked() {
        if (binding.titleEditText.text.toString().isNotBlank()) {
            viewModel.saveMovie(Movie(title = binding.titleEditText.text.toString(), releaseDate = binding.yearEditText.text.toString()))
            finish()
        } else {
            showMessage(getString(R.string.enter_title))
        }
    }

    private fun searchMovieClicked() {
        if (binding.titleEditText.text.toString().isNotBlank()) {
            val intent = Intent(this, SearchMovieActivity::class.java)
            intent.putExtra("title", binding.titleEditText.text.toString())
            startActivity(intent)
        } else {
            showMessage(getString(R.string.enter_title))
        }
    }

    private fun showMessage(msg: String) {
        binding.addLayout.snack((msg), Snackbar.LENGTH_LONG) {
            action(getString(R.string.ok)) {
            }
        }
    }
}