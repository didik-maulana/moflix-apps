package com.didik.moflix.data.movies.datasource.remote

import com.didik.moflix.data.movies.datasource.remote.response.MovieResponse

interface MovieLocalDataSource {
    suspend fun getMovies(): List<MovieResponse>
}