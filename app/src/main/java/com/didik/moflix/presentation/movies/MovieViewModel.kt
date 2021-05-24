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

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _moviesState = MutableLiveData<ViewState<List<MovieModel>>>()
    val movieState: LiveData<ViewState<List<MovieModel>>>
        get() = _moviesState

    private val _movies = MutableLiveData<List<MovieModel>>()
    val movies: LiveData<List<MovieModel>>
        get() = _movies

    fun getMovies() {
        _isLoading.value = true
        viewModelScope.launch(Dispatchers.Main) {
            val result = movieUseCase.getMovies()
            launch(Dispatchers.Main) {
                _isLoading.value = false
                when (result) {
                    is ResultState.Success -> _movies.value = result.data
                    is ResultState.Failure -> ViewState.RenderError(result.error)
                }
            }
        }
    }
}