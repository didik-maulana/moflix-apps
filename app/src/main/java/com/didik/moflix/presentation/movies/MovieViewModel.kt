package com.didik.moflix.presentation.movies

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.didik.moflix.domain.usecase.MovieUseCase
import com.didik.moflix.utils.state.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel @ViewModelInject constructor(
    private val movieUseCase: MovieUseCase
) : ViewModel() {

    private val _movieState = MutableLiveData<MovieState>()
    val movieState: LiveData<MovieState>
        get() = _movieState

    fun getMovies() {
        _movieState.value = MovieState.RenderLoading(true)
        viewModelScope.launch(Dispatchers.IO) {
            val result = movieUseCase.getMovies()
            launch(Dispatchers.Main) {
                _movieState.value = MovieState.RenderLoading(false)
                _movieState.value = when (result) {
                    is ResultState.Success -> MovieState.RenderMovies(result.data)
                    is ResultState.Failure -> MovieState.RenderError(result.error)
                }
            }
        }
    }
}