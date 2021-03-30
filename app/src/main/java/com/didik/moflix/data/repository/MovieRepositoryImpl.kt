package com.didik.moflix.data.repository

import com.didik.moflix.data.datasource.MovieLocalDataSource
import com.didik.moflix.data.mapper.MovieMapper
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val localDataSource: MovieLocalDataSource,
    private val mapper: MovieMapper,
) : MovieRepository {

    override suspend fun getMovies(): List<MovieModel> {
        val movieModelList = localDataSource.getMovies()
        return mapper.mapToListDomain(movieModelList)
    }
}