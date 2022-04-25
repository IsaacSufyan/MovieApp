package com.isaacsufyan.androidarchitecture.mvvm.movieapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.data.db.MovieDao
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.data.model.Movie
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.data.model.MoviesResponse
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.data.net.RetrofitClient
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.db
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.concurrent.thread

class MovieRepositoryImpl : MovieRepository {

  private val movieDao: MovieDao = db.movieDao()
  private val retrofitClient = RetrofitClient()
  private val allMovies: LiveData<List<Movie>> = movieDao.getAll()

  override fun deleteMovie(movie: Movie) {
    thread {
      db.movieDao().delete(movie.id)
    }
  }

  override fun getSavedMovies() = allMovies

  override fun saveMovie(movie: Movie) {
    thread {
      movieDao.insert(movie)
    }
  }

  override fun searchMovies(query: String): LiveData<List<Movie>?> {

    val data = MutableLiveData<List<Movie>>()

    retrofitClient.searchMovies(query).enqueue(object : Callback<MoviesResponse> {
      override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
        data.value = null
        Log.d(this.javaClass.simpleName, "Failure")
      }

      override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
        data.value = response.body()?.results
        Log.d(this.javaClass.simpleName, "Response: ${response.body()?.results}")
      }
    })
    return data
  }
}