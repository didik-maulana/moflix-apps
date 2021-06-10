package com.didik.moflix.data.movies.repository

import androidx.paging.DataSource
import androidx.sqlite.db.SupportSQLiteQuery
import com.didik.moflix.data.movies.datasource.local.MovieLocalDataSource
import com.didik.moflix.data.movies.datasource.remote.MovieRemoteDataSource
import com.didik.moflix.data.movies.mapper.MovieMapper
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.domain.repository.MovieRepository
import com.didik.moflix.utils.state.ResultState
import java.net.UnknownHostException
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val remoteDataSource: MovieRemoteDataSource,
    private val localDataSource: MovieLocalDataSource,
    private val mapper: MovieMapper,
) : MovieRepository {

    override suspend fun getMovies(): ResultState<List<MovieModel>> {
        return try {
            val response = remoteDataSource.getMovies()
            return if (response.isSuccessful) {
                val results = response.body()?.results.orEmpty()
                val movieList = mapper.mapResponseToListDomain(results)
                ResultState.Success(movieList)
            } else {
                ResultState.Failure(response.message())
            }
        } catch (exception: UnknownHostException) {
            ResultState.Failure(exception.message.toString())
        }
    }

    override suspend fun getMovieDetail(movieId: Int): ResultState<MovieModel> {
        return try {
            val response = remoteDataSource.getMovieDetail(movieId)
            val result = response.body()
            return if (response.isSuccessful && result != null) {
                val movie = mapper.mapResponseToDomain(result)
                ResultState.Success(movie)
            } else {
                ResultState.Failure(response.message())
            }
        } catch (exception: UnknownHostException) {
            ResultState.Failure(exception.message.toString())
        }
    }

    override suspend fun getFavoriteMovies(query: SupportSQLiteQuery): DataSource.Factory<Int, MovieModel> {
        return localDataSource.getMovies(query).map(mapper.getMapperEntityToDomain())
    }

    override suspend fun checkFavoriteMovie(movieId: Int): Boolean {
        return localDataSource.getCountMovieById(movieId) > 0
    }

    override suspend fun insertFavoriteMovie(movie: MovieModel) {
        val movieEntity = mapper.mapDomainToEntity(movie)
        localDataSource.insertMovie(movieEntity)
    }

    override suspend fun deleteFavoriteMovie(movie: MovieModel) {
        val movieEntity = mapper.mapDomainToEntity(movie)
        localDataSource.deleteMovie(movieEntity)
    }
}