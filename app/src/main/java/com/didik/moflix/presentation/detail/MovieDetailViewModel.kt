package com.didik.moflix.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.didik.moflix.domain.entity.Movie

class MovieDetailViewModel : ViewModel() {

    private val _movie = MutableLiveData<Movie>()
    val movie: LiveData<Movie> = _movie

    fun loadMovie(movie: Movie?) {
        movie?.let {
            _movie.value = it
        }
    }
}