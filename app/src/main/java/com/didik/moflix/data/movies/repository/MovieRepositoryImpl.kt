package com.didik.moflix.data.movies.repository

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.didik.moflix.data.movies.datasource.local.MovieLocalDataSource
import com.didik.moflix.data.movies.datasource.remote.MovieRemoteDataSource
import com.didik.moflix.data.movies.mapper.MovieMapper
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.domain.repository.MovieRepository
import com.didik.moflix.utils.helpers.FavoriteSortUtils
import com.didik.moflix.utils.helpers.FavoriteSortUtils.Sort
import com.didik.moflix.utils.helpers.FavoriteSortUtils.Type
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

    override suspend fun getFavoriteMovies(sort: Sort): LiveData<PagedList<MovieModel>> {
        val query = FavoriteSortUtils.getSortedQuery(Type.MOVIES, sort)
        val favoriteMovies = localDataSource.getMovies(query).mapByPage { movies ->
            mapper.mapEntityToListDomain(movies)
        }
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(PAGE_CONTENT_SIZE)
            .setPageSize(PAGE_CONTENT_SIZE)
            .build()

        return LivePagedListBuilder(
            favoriteMovies,
            config
        ).build()
    }

    override suspend fun checkFavoriteMovie(movieId: Int): Boolean {
        return localDataSource.getMovieById(movieId) > 0
    }

    override suspend fun insertFavoriteMovie(movie: MovieModel) {
        val movieEntity = mapper.mapDomainToEntity(movie)
        localDataSource.insertMovie(movieEntity)
    }

    override suspend fun deleteFavoriteMovie(movie: MovieModel) {
        val movieEntity = mapper.mapDomainToEntity(movie)
        localDataSource.deleteMovie(movieEntity)
    }

    companion object {
        private const val PAGE_CONTENT_SIZE = 5
    }
}