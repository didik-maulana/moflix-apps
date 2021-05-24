package com.didik.moflix.data.routes

import com.didik.moflix.data.movies.datasource.remote.response.MovieListResponse
import com.didik.moflix.data.movies.datasource.remote.response.MovieResponse
import com.didik.moflix.data.series.datasource.remote.response.SeriesListResponse
import com.didik.moflix.data.series.datasource.remote.response.SeriesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiServices {

    @GET("movie/popular")
    suspend fun getPopularMovies(): Response<MovieListResponse>

    @GET("movie/{id}?append_to_response=credits")
    suspend fun getMovieDetail(@Path("id") movieId: Int): Response<MovieResponse>

    @GET("tv/popular")
    suspend fun getPopularSeries(): Response<SeriesListResponse>

    @GET("tv/{id}?append_to_response=credits")
    suspend fun getSeriesDetail(@Path("id") seriesId: Int): Response<SeriesResponse>
}