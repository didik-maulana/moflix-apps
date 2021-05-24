package com.didik.moflix.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.domain.usecase.MovieUseCase
import com.didik.moflix.domain.usecase.SeriesUseCase
import com.didik.moflix.utils.state.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val movieUseCase: MovieUseCase,
    private val seriesUseCase: SeriesUseCase
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _movie = MutableLiveData<MovieModel>()
    val movie: LiveData<MovieModel>
        get() = _movie

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    fun loadMovieDetail(movieId: Int) {
        viewModelScope.launch {
            _isLoading.value = true

            val result = movieUseCase.getMovieDetail(movieId)
            _isLoading.value = false

            when (result) {
                is ResultState.Success -> _movie.value = result.data
                is ResultState.Failure -> _error.value = result.error
            }
        }
    }

    fun loadSeriesDetail(seriesId: Int) {
        viewModelScope.launch {
            _isLoading.value = true

            val result = seriesUseCase.getSeriesDetail(seriesId)
            _isLoading.value = false

            when (result) {
                is ResultState.Success -> _movie.value = result.data
                is ResultState.Failure -> _error.value = result.error
            }
        }
    }

}