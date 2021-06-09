package com.didik.moflix.domain.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.utils.helpers.FavoriteSortUtils.Sort
import com.didik.moflix.utils.state.ResultState

interface MovieRepository {
    suspend fun getMovies(): ResultState<List<MovieModel>>
    suspend fun getMovieDetail(movieId: Int): ResultState<MovieModel>
    suspend fun getFavoriteMovies(sort: Sort): LiveData<PagedList<MovieModel>>
    suspend fun checkFavoriteMovie(movieId: Int): Boolean
    suspend fun insertFavoriteMovie(movie: MovieModel)
    suspend fun deleteFavoriteMovie(movie: MovieModel)
}