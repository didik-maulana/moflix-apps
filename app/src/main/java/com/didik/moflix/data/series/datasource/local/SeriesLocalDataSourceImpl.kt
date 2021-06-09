package com.didik.moflix.data.series.datasource.local

import androidx.paging.DataSource
import androidx.sqlite.db.SupportSQLiteQuery
import com.didik.moflix.data.series.datasource.local.entities.SeriesEntity
import com.didik.moflix.data.series.datasource.local.room.SeriesDao
import javax.inject.Inject

class SeriesLocalDataSourceImpl @Inject constructor(
    private val seriesDao: SeriesDao
) : SeriesLocalDataSource {

    override fun getSeries(query: SupportSQLiteQuery): DataSource.Factory<Int, SeriesEntity> {
        return seriesDao.getSeries(query)
    }

    override fun getSeriesById(seriesId: Int): Int = seriesDao.getSeriesById(seriesId)

    override fun insertSeries(series: SeriesEntity) = seriesDao.insertSeries(series)

    override fun deleteSeries(series: SeriesEntity) = seriesDao.deleteSeries(series)

}