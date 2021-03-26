package com.didik.moflix.data.datasource

import com.didik.moflix.data.model.SeriesModel

interface SeriesLocalDataSource {
    suspend fun getSeries(): List<SeriesModel>
}