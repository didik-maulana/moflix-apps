package com.didik.moflix.data.movies.datasource.local.room

import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.didik.moflix.data.movies.datasource.local.entities.MovieEntity

@Dao
interface MovieDao {

    @RawQuery(observedEntities = [MovieEntity::class])
    fun getMovies(query: SupportSQLiteQuery): DataSource.Factory<Int, MovieEntity>

    @Query("SELECT COUNT(id) FROM movies WHERE id = :movieId")
    fun getCountMovieById(movieId: Int): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: MovieEntity)

    @Delete
    fun deleteMovie(movie: MovieEntity)
}