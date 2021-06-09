package com.didik.moflix.presentation.features.series.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.domain.usecase.SeriesUseCase
import com.didik.moflix.utils.state.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesDetailViewModel @Inject constructor(
    private val seriesUseCase: SeriesUseCase
) : ViewModel() {

    var seriesId = 0
    var isFirstLoad = true

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _series = MutableLiveData<MovieModel>()
    val series: LiveData<MovieModel>
        get() = _series

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    private val _isFavorite = MutableLiveData<Boolean>()
    val isFavorite: LiveData<Boolean>
        get() = _isFavorite

    fun loadSeriesDetail() {
        viewModelScope.launch {
            _isLoading.value = true

            val result = seriesUseCase.getSeriesDetail(seriesId)
            _isLoading.value = false

            when (result) {
                is ResultState.Success -> _series.value = result.data
                is ResultState.Failure -> _error.value = result.error
            }
        }
    }

    fun checkFavoriteSeries() {
        viewModelScope.launch(Dispatchers.Main) {
            _isFavorite.value = seriesUseCase.checkFavoriteSeries(seriesId)
        }
    }

    fun insertSeries() {
        series.value?.let { series ->
            viewModelScope.launch(Dispatchers.IO) {
                seriesUseCase.insertSeries(series)
            }
        }
    }

    fun deleteSeries() {
        series.value?.let { series ->
            viewModelScope.launch(Dispatchers.IO) {
                seriesUseCase.deleteSeries(series)
            }
        }
    }
}