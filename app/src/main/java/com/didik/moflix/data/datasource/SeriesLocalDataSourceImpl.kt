package com.didik.moflix.data.datasource

import com.didik.moflix.app.AppProvider
import com.didik.moflix.data.model.SeriesModel
import com.didik.moflix.data.response.SeriesResponse
import com.didik.moflix.utils.helpers.JsonHelper

class SeriesLocalDataSourceImpl : SeriesLocalDataSource {

    override suspend fun getSeries(): List<SeriesModel> {
        val series = JsonHelper.readDataFromJson<SeriesResponse>(
            context = AppProvider.context,
            assets = JsonHelper.Assets.SERIES
        )
        return series?.results.orEmpty()
    }
}