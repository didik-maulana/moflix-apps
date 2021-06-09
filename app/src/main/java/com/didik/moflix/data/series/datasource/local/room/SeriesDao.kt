package com.didik.moflix.data.series.datasource.local.room

import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.didik.moflix.data.series.datasource.local.entities.SeriesEntity

@Dao
interface SeriesDao {

    @RawQuery(observedEntities = [SeriesEntity::class])
    fun getSeries(query: SupportSQLiteQuery): DataSource.Factory<Int, SeriesEntity>

    @Query("SELECT COUNT(id) FROM series WHERE id = :seriesId")
    fun getSeriesById(seriesId: Int): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSeries(series: SeriesEntity)

    @Delete
    fun deleteSeries(series: SeriesEntity)
}