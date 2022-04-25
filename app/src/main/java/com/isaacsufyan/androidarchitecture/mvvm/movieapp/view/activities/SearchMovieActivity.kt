package com.isaacsufyan.androidarchitecture.mvvm.movieapp.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.R
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.action
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.data.model.Movie
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.databinding.ActivitySearchBinding
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.snack
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.view.adapters.SearchListAdapter
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.viewmodel.SearchViewModel

class SearchMovieActivity : BaseActivity() {

    private lateinit var binding: ActivitySearchBinding
    private var adapter = SearchListAdapter(mutableListOf()) { movie -> displayConfirmation(movie) }
    private lateinit var title: String

    private val viewModel by lazy {
        ViewModelProvider(this)[SearchViewModel::class.java]
    }

    override fun getToolbarInstance(): Toolbar = binding.toolbarView.toolbarToolbarView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent?.extras?.getString("title")?.let {
            title = it
        }

        binding.searchRecyclerView.adapter = adapter
        searchMovie()
    }

    private fun displayConfirmation(movie: Movie) {
        binding.searchLayout.snack("Add ${movie.title} to your list?", Snackbar.LENGTH_LONG) {
            action(getString(R.string.ok)) {
                viewModel.saveMovie(movie)
                val intent = Intent(this@SearchMovieActivity, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }

    private fun searchMovie() {
        showLoading()
        viewModel.searchMovie(title).observe(this) { movies ->
            hideLoading()
            if (movies == null) {
                showMessage()
            } else {
                adapter.setMovies(movies)
            }
        }
    }

    private fun showLoading() {
        binding.searchProgressBar.visibility = View.VISIBLE
        binding.searchRecyclerView.isEnabled = false
    }

    private fun hideLoading() {
        binding.searchProgressBar.visibility = View.GONE
        binding.searchRecyclerView.isEnabled = true
    }

    private fun showMessage() {
        binding.searchLayout.snack(getString(R.string.network_error), Snackbar.LENGTH_INDEFINITE) {
            action(getString(R.string.ok)) {
                searchMovie()
            }
        }
    }
}