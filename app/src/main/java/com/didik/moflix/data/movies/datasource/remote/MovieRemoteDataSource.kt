package com.didik.moflix.data.movies.datasource.remote

import com.didik.moflix.data.movies.datasource.remote.response.MovieListResponse
import com.didik.moflix.data.movies.datasource.remote.response.MovieResponse
import retrofit2.Response

interface MovieRemoteDataSource {
    suspend fun getMovies(): Response<MovieListResponse>
    suspend fun getMovieDetail(movieId: Int): Response<MovieResponse>
}