package com.didik.moflix.data.series.datasource.local

import androidx.paging.DataSource
import androidx.sqlite.db.SupportSQLiteQuery
import com.didik.moflix.data.series.datasource.local.entities.SeriesEntity

interface SeriesLocalDataSource {
    fun getSeries(query: SupportSQLiteQuery): DataSource.Factory<Int, SeriesEntity>
    fun getCountSeriesById(seriesId: Int): Int
    fun insertSeries(series: SeriesEntity)
    fun deleteSeries(series: SeriesEntity)
}