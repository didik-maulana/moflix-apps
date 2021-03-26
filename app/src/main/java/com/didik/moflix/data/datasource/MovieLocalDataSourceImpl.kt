package com.didik.moflix.data.datasource

import com.didik.moflix.app.AppProvider
import com.didik.moflix.data.model.MovieModel
import com.didik.moflix.data.response.MovieResponse
import com.didik.moflix.utils.helpers.JsonHelper

class MovieLocalDataSourceImpl : MovieLocalDataSource {

    override suspend fun getMovies(): List<MovieModel> {
        val movieList = JsonHelper.readDataFromJson<MovieResponse>(
            context = AppProvider.context,
            assets = JsonHelper.Assets.MOVIES
        )
        return movieList?.results.orEmpty()
    }
}