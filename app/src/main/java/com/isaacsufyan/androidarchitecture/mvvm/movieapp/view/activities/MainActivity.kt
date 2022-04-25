package com.isaacsufyan.androidarchitecture.mvvm.movieapp.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.view.adapters.MovieListAdapter
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.R
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.databinding.ActivityMainBinding
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.viewmodel.MainViewModel

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter = MovieListAdapter(mutableListOf())
    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    override fun getToolbarInstance(): Toolbar {
        return binding.toolbarView.toolbarToolbarView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.moviesRecyclerView.adapter = adapter
        showLoading()

        viewModel.getSavedMovies().observe(this) { movies ->
            hideLoading()
            movies?.let {
                adapter.setMovies(movies)
            }
        }

        binding.fab.setOnClickListener {
            startActivity(Intent(this, AddMovieActivity::class.java))
        }
    }

    private fun showLoading() {
        binding.moviesRecyclerView.isEnabled = false
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.moviesRecyclerView.isEnabled = true
        binding.progressBar.visibility = View.GONE
    }

    private fun deleteMoviesClicked() {
        for (movie in adapter.selectedMovies) {
            viewModel.deleteSavedMovies(movie)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> this.deleteMoviesClicked()
            else -> {
                Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}