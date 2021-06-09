package com.didik.moflix.domain.repository

import androidx.paging.DataSource
import androidx.sqlite.db.SupportSQLiteQuery
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.utils.state.ResultState

interface MovieRepository {
    suspend fun getMovies(): ResultState<List<MovieModel>>
    suspend fun getMovieDetail(movieId: Int): ResultState<MovieModel>
    suspend fun getFavoriteMovies(query: SupportSQLiteQuery): DataSource.Factory<Int, MovieModel>
    suspend fun checkFavoriteMovie(movieId: Int): Boolean
    suspend fun insertFavoriteMovie(movie: MovieModel)
    suspend fun deleteFavoriteMovie(movie: MovieModel)
}