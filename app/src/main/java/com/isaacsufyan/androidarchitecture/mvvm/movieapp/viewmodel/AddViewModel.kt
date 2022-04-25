package com.isaacsufyan.androidarchitecture.mvvm.movieapp.viewmodel

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.data.MovieRepository
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.data.MovieRepositoryImpl
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.data.model.Movie

class AddViewModel(private val repository: MovieRepository = MovieRepositoryImpl()) : ViewModel() {

    var title = ObservableField("")
    var releaseDate = ObservableField("")

    private val _saveMovie = MutableLiveData<Boolean>()
    private val _searchMovie = MutableLiveData<String>()
    val saveMovie : LiveData<Boolean> = _saveMovie
    val searchMovie : LiveData<String> = _searchMovie

    fun saveMovie() {
        if (validateCanSaveMovie()) {
            repository.saveMovie(Movie(title = title.get(), releaseDate = releaseDate.get()))
            _saveMovie.postValue(true)
        } else {
            _saveMovie.postValue(false)
        }
    }

    fun openSearchScreen(){
        if (validateCanSaveSearch()){
            _searchMovie.postValue(title.get())
        }else{
            _searchMovie.postValue(null)
        }
    }

    fun validateCanSaveMovie(): Boolean {
        val title = this.title.get()
        val releaseDate = this.releaseDate.get()

        if (title != null && releaseDate != null) {
            return title.isNotEmpty() && releaseDate.isNotEmpty()
        }
        return false
    }

    fun validateCanSaveSearch(): Boolean {
        val title = this.title.get()
        if (!title.isNullOrBlank()) {
            return true
        }
        return false
    }
}