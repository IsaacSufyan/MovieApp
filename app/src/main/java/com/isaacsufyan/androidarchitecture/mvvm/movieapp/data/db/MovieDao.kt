package com.isaacsufyan.androidarchitecture.mvvm.movieapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.data.model.Movie

@Dao
interface MovieDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(movie: Movie)

  @Query("select * from movie")
  fun getAll(): LiveData<List<Movie>>

  @Query("delete from movie where watched = :watched")
  fun deleteMovies(watched: Boolean)

  @Update
  fun updateMovie(movie: Movie)

  @Query("DELETE FROM movie WHERE id = :id")
  fun delete(id: Int?)

}