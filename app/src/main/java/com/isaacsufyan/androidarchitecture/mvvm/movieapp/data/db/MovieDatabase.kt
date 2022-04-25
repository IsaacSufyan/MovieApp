package com.isaacsufyan.androidarchitecture.mvvm.movieapp.data.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.isaacsufyan.androidarchitecture.mvvm.movieapp.data.model.Movie

@Database(entities = [Movie::class], version = 1)
@TypeConverters(GenreIdConverter::class)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao

    companion object {
        private val lock = Any()
        private const val DB_NAME = "MovieDatabase"
        private var INSTANCE: MovieDatabase? = null

        fun getInstance(application: Application): MovieDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE =
                        Room.databaseBuilder(application, MovieDatabase::class.java, DB_NAME)
                            .build()
                }
            }
            return INSTANCE!!
        }
    }
}