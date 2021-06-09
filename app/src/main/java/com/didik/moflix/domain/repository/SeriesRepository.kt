package com.didik.moflix.domain.repository

import androidx.paging.DataSource
import androidx.sqlite.db.SupportSQLiteQuery
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.utils.state.ResultState

interface SeriesRepository {
    suspend fun getSeries(): ResultState<List<MovieModel>>
    suspend fun getSeriesDetail(seriesId: Int): ResultState<MovieModel>
    suspend fun getFavoriteSeries(query: SupportSQLiteQuery): DataSource.Factory<Int, MovieModel>
    suspend fun checkFavoriteSeries(seriesId: Int): Boolean
    suspend fun insertFavoriteSeries(series: MovieModel)
    suspend fun deleteFavoriteSeries(series: MovieModel)
}