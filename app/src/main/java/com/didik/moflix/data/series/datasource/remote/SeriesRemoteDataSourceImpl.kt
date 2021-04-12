package com.didik.moflix.data.series.datasource.remote

import com.didik.moflix.data.series.datasource.remote.response.SeriesResponse
import com.didik.moflix.utils.helpers.JSONHelper

class SeriesRemoteDataSourceImpl : SeriesRemoteDataSource {

    override suspend fun getSeries(): List<SeriesResponse> {
        val seriesResponse = JSONHelper.readSeriesJson()
        return seriesResponse?.results.orEmpty()
    }
}