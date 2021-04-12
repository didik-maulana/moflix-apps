package com.didik.moflix.data.movies.repository

import com.didik.moflix.data.movies.datasource.remote.MovieLocalDataSource
import com.didik.moflix.data.movies.mapper.MovieMapper
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val localDataSource: MovieLocalDataSource,
    private val mapper: MovieMapper,
) : MovieRepository {

    override suspend fun getMovies(): List<MovieModel> {
        val movieList = localDataSource.getMovies()
        return mapper.mapToListDomain(movieList)
    }
}