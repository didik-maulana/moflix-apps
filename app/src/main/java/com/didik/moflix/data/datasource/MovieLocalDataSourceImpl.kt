package com.didik.moflix.data.datasource

import com.didik.moflix.data.response.MovieResponse
import com.didik.moflix.utils.helpers.JSONHelper

class MovieLocalDataSourceImpl : MovieLocalDataSource {

    override suspend fun getMovies(): List<MovieResponse> {
        val movieResponse = JSONHelper.readMoviesJson()
        return movieResponse?.results.orEmpty()
    }
}