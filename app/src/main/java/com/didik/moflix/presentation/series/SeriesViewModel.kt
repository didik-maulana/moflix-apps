package com.didik.moflix.presentation.series

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.domain.usecase.SeriesUseCase
import com.didik.moflix.utils.state.ResultState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SeriesViewModel @Inject constructor(
    private val seriesUseCase: SeriesUseCase
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _series = MutableLiveData<List<MovieModel>>()
    val series: LiveData<List<MovieModel>>
        get() = _series

    private val _error = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _error

    fun getSeries() {
        viewModelScope.launch {
            _isLoading.value = true

            val result = seriesUseCase.getSeries()
            _isLoading.value = false

            when (result) {
                is ResultState.Success -> _series.value = result.data
                is ResultState.Failure -> _error.value = result.error
            }
        }
    }
}