package com.didik.moflix.data.movies.datasource.local

import androidx.paging.DataSource
import androidx.sqlite.db.SupportSQLiteQuery
import com.didik.moflix.data.movies.datasource.local.entities.MovieEntity

interface MovieLocalDataSource {
    fun getMovies(query: SupportSQLiteQuery): DataSource.Factory<Int, MovieEntity>
    fun getCountMovieById(movieId: Int): Int
    fun insertMovie(movie: MovieEntity)
    fun deleteMovie(movie: MovieEntity)
}