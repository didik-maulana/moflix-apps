package com.didik.moflix.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.utils.helpers.FavoriteSortUtils.Sort
import com.didik.moflix.utils.state.ResultState

interface SeriesRepository {
    suspend fun getSeries(): ResultState<List<MovieModel>>
    suspend fun getSeriesDetail(seriesId: Int): ResultState<MovieModel>
    suspend fun getFavoriteSeries(sort: Sort): LiveData<PagedList<MovieModel>>
    suspend fun checkFavoriteSeries(seriesId: Int): Boolean
    suspend fun insertFavoriteSeries(series: MovieModel)
    suspend fun deleteFavoriteSeries(series: MovieModel)
}