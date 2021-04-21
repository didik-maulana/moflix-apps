package com.didik.moflix.presentation.series

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.domain.usecase.SeriesUseCase
import com.didik.moflix.utils.state.ResultState
import com.didik.moflix.utils.state.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SeriesViewModel @ViewModelInject constructor(
    private val seriesUseCase: SeriesUseCase
) : ViewModel() {

    private val _seriesState = MutableLiveData<ViewState<List<MovieModel>>>()
    val seriesState: LiveData<ViewState<List<MovieModel>>>
        get() = _seriesState

    fun getSeries() {
        _seriesState.value = ViewState.RenderLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            val result = seriesUseCase.getSeries()
            launch(Dispatchers.Main) {
                _seriesState.value = ViewState.RenderLoading(false)
                _seriesState.value = when (result) {
                    is ResultState.Success -> ViewState.RenderData(result.data)
                    is ResultState.Failure -> ViewState.RenderError(result.error)
                }
            }
        }
    }
}