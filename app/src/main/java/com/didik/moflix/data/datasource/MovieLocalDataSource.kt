package com.didik.moflix.data.datasource

import com.didik.moflix.data.response.MovieResponse

interface MovieLocalDataSource {
    suspend fun getMovies(): List<MovieResponse>
}