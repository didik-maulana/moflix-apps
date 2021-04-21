package com.didik.moflix.domain.usecase

import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.domain.repository.MovieRepository
import com.didik.moflix.utils.state.ResultState
import javax.inject.Inject

class MovieUseCase @Inject constructor(private val repository: MovieRepository) {

    suspend fun getMovies(): ResultState<List<MovieModel>> {
        return repository.getMovies()
    }

    suspend fun getMovieDetail(movieId: Int): ResultState<MovieModel> {
        return repository.getMovieDetail(movieId)
    }
}