package com.didik.moflix.domain.usecase

import com.didik.moflix.domain.entity.Movie
import com.didik.moflix.domain.repository.MovieRepository
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(private val repository: MovieRepository) {

    suspend fun getMovies(): List<Movie> {
        return repository.getMovies()
    }
}