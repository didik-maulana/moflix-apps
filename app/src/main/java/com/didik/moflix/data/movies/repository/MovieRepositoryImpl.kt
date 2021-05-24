package com.didik.moflix.data.movies.repository

import com.didik.moflix.data.movies.datasource.remote.MovieRemoteDataSource
import com.didik.moflix.data.movies.mapper.MovieMapper
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.domain.repository.MovieRepository
import com.didik.moflix.utils.state.ResultState
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val mapper: MovieMapper,
) : MovieRepository {

    override suspend fun getMovies(): ResultState<List<MovieModel>> {
        val response = remoteDataSource.getMovies()
        return if (response.isSuccessful) {
            val results = response.body()?.results.orEmpty()
            val movieList = mapper.mapToListDomain(results)
            ResultState.Success(movieList)
        } else {
            ResultState.Failure(response.message())
        }
    }

    override suspend fun getMovieDetail(movieId: Int): ResultState<MovieModel> {
        val response = remoteDataSource.getMovieDetail(movieId)
        val result = response.body()
        return if (response.isSuccessful && result != null) {
            val movie = mapper.mapToDomain(result)
            ResultState.Success(movie)
        } else {
            ResultState.Failure(response.message())
        }
    }
}