package com.didik.moflix.data.datasource

import com.didik.moflix.data.response.SeriesResponse

interface SeriesLocalDataSource {
    suspend fun getSeries(): List<SeriesResponse>
}