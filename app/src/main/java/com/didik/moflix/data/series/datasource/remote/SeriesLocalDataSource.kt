package com.didik.moflix.data.series.datasource.remote

import com.didik.moflix.data.series.datasource.remote.response.SeriesResponse

interface SeriesLocalDataSource {
    suspend fun getSeries(): List<SeriesResponse>
}