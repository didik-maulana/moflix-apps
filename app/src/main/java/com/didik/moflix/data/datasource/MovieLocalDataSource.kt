package com.didik.moflix.data.datasource

import com.didik.moflix.data.model.MovieModel

interface MovieLocalDataSource {
    suspend fun getMovies(): List<MovieModel>
}