package com.didik.moflix.presentation.features.favorites.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.domain.usecase.MovieUseCase
import com.didik.moflix.utils.helpers.FavoriteSortUtils
import com.didik.moflix.utils.helpers.FavoriteSortUtils.Sort
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteMoviesViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase
) : ViewModel() {

    var isSortedList = false

    suspend fun getFavoriteMovies(sort: Sort): LiveData<PagedList<MovieModel>> {
        val query = FavoriteSortUtils.getMovieSortedQuery(sort)
        val favoriteMovies = movieUseCase.getFavoriteMovies(query)
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setInitialLoadSizeHint(PAGE_CONTENT_SIZE)
            .setPageSize(PAGE_CONTENT_SIZE)
            .build()

        return LivePagedListBuilder(
            favoriteMovies,
            config
        ).build()
    }

    companion object {
        private const val PAGE_CONTENT_SIZE = 5
    }
}