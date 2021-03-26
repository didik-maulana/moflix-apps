package com.didik.moflix.presentation.series

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.didik.moflix.domain.entity.Movie
import com.didik.moflix.domain.usecase.GetSeriesUseCase
import kotlinx.coroutines.launch

class SeriesViewModel @ViewModelInject constructor(
    private val getSeriesUseCase: GetSeriesUseCase
) : ViewModel() {

    private val _seriesList = MutableLiveData<List<Movie>>()
    val seriesList: LiveData<List<Movie>>
        get() = _seriesList

    fun getSeries() {
        viewModelScope.launch {
            _seriesList.postValue(getSeriesUseCase.getSeries())
        }
    }
}