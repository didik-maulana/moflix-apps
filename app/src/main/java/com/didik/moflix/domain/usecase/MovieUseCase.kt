package com.didik.moflix.domain.usecase

import androidx.paging.DataSource
import androidx.sqlite.db.SupportSQLiteQuery
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.domain.repository.MovieRepository
import com.didik.moflix.utils.dispatcher.DispatchersProvider
import com.didik.moflix.utils.state.ResultState
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieUseCase @Inject constructor(
    private val repository: MovieRepository,
    private val dispatchers: DispatchersProvider
) {

    suspend fun getMovies(): ResultState<List<MovieModel>> {
        return withContext(dispatchers.io) {
            repository.getMovies()
        }
    }

    suspend fun getMovieDetail(movieId: Int): ResultState<MovieModel> {
        return withContext(dispatchers.io) {
            repository.getMovieDetail(movieId)
        }
    }

    suspend fun getFavoriteMovies(query: SupportSQLiteQuery): DataSource.Factory<Int, MovieModel> {
        return withContext(dispatchers.io) {
            repository.getFavoriteMovies(query)
        }
    }

    suspend fun checkFavoriteMovie(movieId: Int): Boolean {
        return withContext(dispatchers.io) {
            repository.checkFavoriteMovie(movieId)
        }
    }

    suspend fun insertFavoriteMovie(movie: MovieModel?) {
        if (movie == null) return
        repository.insertFavoriteMovie(movie)
    }

    suspend fun deleteFavoriteMovie(movie: MovieModel?) {
        if (movie == null) return
        repository.deleteFavoriteMovie(movie)
    }
}