package com.isaacsufyan.androidarchitecture.mvvm.movieapp.data

import androidx.lifecycle.LiveData
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.data.model.Movie

interface MovieRepository {

    fun getSavedMovies(): LiveData<List<Movie>>

    fun saveMovie(movie: Movie)

    fun deleteMovie(movie: Movie)

    fun searchMovies(query: String): LiveData<List<Movie>?>

}