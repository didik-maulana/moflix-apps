package com.didik.moflix.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.paging.PagedList
import com.didik.moflix.data.movies.datasource.local.entities.MovieEntity
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.domain.repository.MovieRepository
import com.didik.moflix.utils.dispatcher.DispatchersProvider
import com.didik.moflix.utils.helpers.FavoriteSortUtils
import com.didik.moflix.utils.helpers.FavoriteSortUtils.Sort
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

    suspend fun getFavoriteMovies(sort: Sort): LiveData<PagedList<MovieModel>> {
        return withContext(dispatchers.io) {
            repository.getFavoriteMovies(sort)
        }
    }

    suspend fun checkFavoriteMovie(movieId: Int): Boolean {
        return withContext(dispatchers.io) {
            repository.checkFavoriteMovie(movieId)
        }
    }

    suspend fun insertMovie(movie: MovieModel) = repository.insertFavoriteMovie(movie)

    suspend fun deleteMovie(movie: MovieModel) = repository.deleteFavoriteMovie(movie)
}