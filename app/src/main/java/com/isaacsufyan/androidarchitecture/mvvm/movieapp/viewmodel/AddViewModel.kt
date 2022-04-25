package com.isaacsufyan.androidarchitecture.mvvm.movieapp.viewmodel

import androidx.lifecycle.ViewModel
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.data.MovieRepository
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.data.MovieRepositoryImpl
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.data.model.Movie

class AddViewModel(private val repository: MovieRepository = MovieRepositoryImpl()): ViewModel()  {

  fun saveMovie(movie: Movie) {
    repository.saveMovie(movie)
  }
}