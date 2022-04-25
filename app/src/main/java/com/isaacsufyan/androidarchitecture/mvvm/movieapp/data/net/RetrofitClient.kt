package com.isaacsufyan.androidarchitecture.mvvm.movieapp.data.net

import com.isaacsufyan.androidarchitecture.mvvm.movieapp.data.model.MoviesResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {

    private val moviesApi: MoviesApi

    companion object {
        private const val API_KEY = "Place_Your_Id"
        private const val BASE_URL = "http://api.themoviedb.org/3/"
        const val IMAGE_URL = "https://image.tmdb.org/t/p/w500/"
    }

    init {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val builder = OkHttpClient.Builder()
        val okHttpClient = builder.addInterceptor(interceptor).build()


        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        moviesApi = retrofit.create(MoviesApi::class.java)
    }


    fun searchMovies(query: String): Call<MoviesResponse> {
        return moviesApi.searchMovie(API_KEY, query)
    }
}