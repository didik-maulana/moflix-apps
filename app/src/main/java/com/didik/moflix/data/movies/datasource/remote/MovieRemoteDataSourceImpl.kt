package com.didik.moflix.data.movies.datasource.remote

import com.didik.moflix.data.movies.datasource.remote.response.MovieListResponse
import com.didik.moflix.data.routes.ApiServices
import retrofit2.Response
import javax.inject.Inject

class MovieRemoteDataSourceImpl @Inject constructor(
    private val apiServices: ApiServices
) : MovieRemoteDataSource {

    override suspend fun getMovies(): Response<MovieListResponse> {
        return apiServices.getPopularMovies()
    }
}