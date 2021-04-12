package com.didik.moflix.data.movies.datasource.remote

import com.didik.moflix.data.movies.datasource.remote.response.MovieResponse

interface MovieRemoteDataSource {
    suspend fun getMovies(): List<MovieResponse>
}