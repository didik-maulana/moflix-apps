package com.didik.moflix.presentation.features.favorites.series

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.domain.usecase.SeriesUseCase
import com.didik.moflix.utils.helpers.FavoriteSortUtils
import com.didik.moflix.utils.helpers.FavoriteSortUtils.Sort
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteSeriesViewModel @Inject constructor(
    private val seriesUseCase: SeriesUseCase
) : ViewModel() {

    var isSortedList = false

    suspend fun getSeries(sort: Sort): LiveData<PagedList<MovieModel>> {
        val query = FavoriteSortUtils.getSeriesSortedQuery(sort)
        val favoriteSeries = seriesUseCase.getFavoriteSeries(query)
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(PAGE_CONTENT_SIZE)
            .setPageSize(PAGE_CONTENT_SIZE)
            .build()

        return LivePagedListBuilder(
            favoriteSeries,
            config
        ).build()
    }

    companion object {
        private const val PAGE_CONTENT_SIZE = 5
    }

}