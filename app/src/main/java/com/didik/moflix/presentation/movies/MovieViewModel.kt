package com.didik.moflix.presentation.movies

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

class MovieViewModel @ViewModelInject constructor(
    private val movieUseCase: MovieUseCase
) : ViewModel() {

    private val _moviesState = MutableLiveData<ViewState<List<MovieModel>>>()
    val movieState: LiveData<ViewState<List<MovieModel>>>
        get() = _moviesState

    fun getMovies() {
        _moviesState.value = ViewState.RenderLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            val result = movieUseCase.getMovies()
            launch(Dispatchers.Main) {
                _moviesState.value = ViewState.RenderLoading(false)
                _moviesState.value = when (result) {
                    is ResultState.Success -> ViewState.RenderData(result.data)
                    is ResultState.Failure -> ViewState.RenderError(result.error)
                }
            }
        }
    }
}