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
        return try {
            val response = remoteDataSource.getMovies()
            if (response.isSuccessful) {
                val results = response.body()?.results.orEmpty()
                val movieList = mapper.mapToListDomain(results)
                ResultState.Success(movieList)
            } else {
                ResultState.Failure(response.message())
            }
        } catch (exception: Exception) {
            ResultState.Failure(exception.message.orEmpty())
        }
    }
}