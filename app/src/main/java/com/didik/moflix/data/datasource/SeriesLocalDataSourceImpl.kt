package com.didik.moflix.data.datasource

import com.didik.moflix.app.AppProvider
import com.didik.moflix.data.response.SeriesResponse
import com.didik.moflix.utils.helpers.JsonHelper

class SeriesLocalDataSourceImpl : SeriesLocalDataSource {

    override suspend fun getSeries(): List<SeriesResponse> {
        val seriesResponse = JsonHelper.readSeriesJson(AppProvider.context)
        return seriesResponse?.results.orEmpty()
    }
}