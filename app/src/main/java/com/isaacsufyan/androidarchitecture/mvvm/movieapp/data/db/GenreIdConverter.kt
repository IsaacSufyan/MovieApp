package com.isaacsufyan.androidarchitecture.mvvm.movieapp.data.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class GenreIdConverter {

    private val gson = Gson()

    @TypeConverter
    fun stringToGenreList(data: String?): List<Int> {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun genreListToString(genreIds: List<Int>?): String {
        if (genreIds == null) {
            return gson.toJson(Collections.emptyList<Int>())
        }
        return gson.toJson(genreIds)
    }
}