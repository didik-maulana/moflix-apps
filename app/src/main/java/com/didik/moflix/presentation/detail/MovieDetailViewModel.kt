package com.didik.moflix.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.didik.moflix.domain.model.MovieModel

class MovieDetailViewModel : ViewModel() {

    private val _movie = MutableLiveData<MovieModel>()
    val movie: LiveData<MovieModel> = _movie

    fun loadMovie(movie: MovieModel?) {
        movie?.let {
            _movie.value = it
        }
    }
}