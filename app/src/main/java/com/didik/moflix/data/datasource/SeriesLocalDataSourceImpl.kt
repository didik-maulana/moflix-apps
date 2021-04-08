package com.didik.moflix.data.datasource

import com.didik.moflix.data.response.SeriesResponse
import com.didik.moflix.utils.helpers.JSONHelper

class SeriesLocalDataSourceImpl : SeriesLocalDataSource {

    override suspend fun getSeries(): List<SeriesResponse> {
        val seriesResponse = JSONHelper.readSeriesJson()
        return seriesResponse?.results.orEmpty()
    }
}