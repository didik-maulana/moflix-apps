package com.didik.moflix.presentation.series

import androidx.annotation.VisibleForTesting
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.domain.usecase.GetSeriesUseCase
import kotlinx.coroutines.launch

class SeriesViewModel @ViewModelInject constructor(
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val getSeriesUseCase: GetSeriesUseCase
) : ViewModel() {

    private val _seriesList = MutableLiveData<List<MovieModel>>()
    val seriesList: LiveData<List<MovieModel>>
        get() = _seriesList

    fun getSeries() {
        viewModelScope.launch {
            _seriesList.postValue(getSeriesUseCase.getSeries())
        }
    }
}