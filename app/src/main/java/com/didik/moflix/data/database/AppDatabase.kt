package com.didik.moflix.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.didik.moflix.data.movies.datasource.local.entities.MovieEntity
import com.didik.moflix.data.movies.datasource.local.room.MovieDao
import com.didik.moflix.data.series.datasource.local.entities.SeriesEntity
import com.didik.moflix.data.series.datasource.local.room.SeriesDao

@Database(
    entities = [
        MovieEntity::class,
        SeriesEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun seriesDao(): SeriesDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "Moflix.db"
                ).build().apply {
                    INSTANCE = this
                }
            }
        }
    }
}