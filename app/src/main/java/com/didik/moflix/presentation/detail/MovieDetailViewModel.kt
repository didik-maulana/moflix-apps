package com.didik.moflix.presentation.detail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.domain.usecase.MovieUseCase
import com.didik.moflix.utils.state.ResultState
import com.didik.moflix.utils.state.ViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieDetailViewModel @ViewModelInject constructor(
    private val movieUseCase: MovieUseCase
) : ViewModel() {

    var movie: MovieModel? = null
        private set

    private val _movieState = MutableLiveData<ViewState<MovieModel>>()
    val movieState: LiveData<ViewState<MovieModel>>
        get() = _movieState

    fun loadMovieDetail(movieId: Int) {
        _movieState.value = ViewState.RenderLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            val result = movieUseCase.getMovieDetail(movieId)
            launch(Dispatchers.Main) {
                _movieState.value = ViewState.RenderLoading(false)
                _movieState.value = when (result) {
                    is ResultState.Success -> {
                        movie = result.data
                        ViewState.RenderData(result.data)
                    }
                    is ResultState.Failure -> ViewState.RenderError(result.error)
                }
            }
        }
    }
}