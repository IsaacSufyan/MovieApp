package com.isaacsufyan.androidarchitecture.mvvm.movieapp.viewmodel

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.data.MovieRepository
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.data.MovieRepositoryImpl
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.data.model.Movie

class MainViewModel(private val repository: MovieRepository = MovieRepositoryImpl()) : ViewModel() {

    private val allMovies = MediatorLiveData<List<Movie>>()

    init {
        getAllMovies()
    }

    fun getSavedMovies() = allMovies

    private fun getAllMovies() {
        allMovies.addSource(repository.getSavedMovies()) { movies ->
            allMovies.postValue(movies)
        }
    }

    fun deleteSavedMovies(movie: Movie) {
        repository.deleteMovie(movie)
    }
}