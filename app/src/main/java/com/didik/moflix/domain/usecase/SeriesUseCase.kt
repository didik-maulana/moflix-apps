package com.didik.moflix.domain.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.domain.repository.SeriesRepository
import com.didik.moflix.utils.dispatcher.DispatchersProvider
import com.didik.moflix.utils.helpers.FavoriteSortUtils.Sort
import com.didik.moflix.utils.state.ResultState
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SeriesUseCase @Inject constructor(
    private val repository: SeriesRepository,
    private val dispatchers: DispatchersProvider
) {

    suspend fun getSeries(): ResultState<List<MovieModel>> {
        return withContext(dispatchers.io) {
            repository.getSeries()
        }
    }

    suspend fun getSeriesDetail(seriesId: Int): ResultState<MovieModel> {
        return withContext(dispatchers.io) {
            repository.getSeriesDetail(seriesId)
        }
    }

    suspend fun getFavoriteSeries(sort: Sort): LiveData<PagedList<MovieModel>> {
        return withContext(dispatchers.io) {
            repository.getFavoriteSeries(sort)
        }
    }

    suspend fun checkFavoriteSeries(seriesId: Int): Boolean {
        return withContext(dispatchers.io) {
            repository.checkFavoriteSeries(seriesId)
        }
    }

    suspend fun insertSeries(series: MovieModel) {
        repository.insertFavoriteSeries(series)
    }

    suspend fun deleteSeries(series: MovieModel) {
        repository.deleteFavoriteSeries(series)
    }

}