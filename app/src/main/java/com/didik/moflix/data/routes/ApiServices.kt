package com.didik.moflix.data.routes

import com.didik.moflix.data.movies.datasource.remote.response.MovieListResponse
import com.didik.moflix.data.series.datasource.remote.response.SeriesListResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiServices {

    @GET("movie/popular")
    suspend fun getPopularMovies(): Response<MovieListResponse>

    @GET("tv/popular")
    suspend fun getPopularSeries(): Response<SeriesListResponse>
}