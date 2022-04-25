package com.isaacsufyan.androidarchitecture.mvvm.movieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.data.MovieRepository
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.data.MovieRepositoryImpl
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.data.model.Movie

class SearchViewModel(private val repository: MovieRepository = MovieRepositoryImpl()): ViewModel()  {

  fun searchMovie(query: String): LiveData<List<Movie>?> {
    return repository.searchMovies(query)
  }

  fun saveMovie(movie: Movie) {
    repository.saveMovie(movie)
  }
}