package com.didik.moflix.data.movies.datasource.local

import androidx.paging.DataSource
import androidx.sqlite.db.SupportSQLiteQuery
import com.didik.moflix.data.movies.datasource.local.entities.MovieEntity
import com.didik.moflix.data.movies.datasource.local.room.MovieDao
import javax.inject.Inject

class MovieLocalDataSourceImpl @Inject constructor(
    private val movieDao: MovieDao
) : MovieLocalDataSource {

    override fun getMovies(query: SupportSQLiteQuery): DataSource.Factory<Int, MovieEntity> {
        return movieDao.getMovies(query)
    }

    override fun getMovieById(movieId: Int): Int = movieDao.getMovieById(movieId)

    override fun insertMovie(movie: MovieEntity) = movieDao.insertMovie(movie)

    override fun deleteMovie(movie: MovieEntity) = movieDao.deleteMovie(movie)

}