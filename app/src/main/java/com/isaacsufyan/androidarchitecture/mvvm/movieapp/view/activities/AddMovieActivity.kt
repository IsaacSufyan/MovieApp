package com.isaacsufyan.androidarchitecture.mvvm.movieapp.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
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
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add)
        binding.viewModel = viewModel
        subscribeObservers()
    }

    private fun showMessage(msg: String) {
        binding.addLayout.snack((msg), Snackbar.LENGTH_LONG) {
            action(getString(R.string.ok)) {
            }
        }
    }

    private fun subscribeObservers() {
        viewModel.saveMovie.observe(this) { saved ->
            saved?.let {
                if (saved) {
                    finish()
                } else {
                    showMessage(getString(R.string.message_validate_can_movie_saved))
                }
            }
        }

        viewModel.searchMovie.observe(this) {
            it?.let {
                val intent = Intent(this, SearchMovieActivity::class.java)
                intent.putExtra("title", it)
                startActivity(intent)
            } ?: run {
                showMessage(getString(R.string.message_validate_can_movie_search))
            }
        }
    }

}