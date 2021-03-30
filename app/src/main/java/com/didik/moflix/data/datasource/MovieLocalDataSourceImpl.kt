package com.didik.moflix.data.datasource

import com.didik.moflix.data.response.MovieResponse
import com.didik.moflix.utils.helpers.JsonHelper

class MovieLocalDataSourceImpl : MovieLocalDataSource {

    override suspend fun getMovies(): List<MovieResponse> {
        val movieResponse = JsonHelper.readMoviesJson()
        return movieResponse?.results.orEmpty()
    }
}