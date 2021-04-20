package com.didik.moflix.presentation.movies

import com.didik.moflix.domain.model.MovieModel

sealed class MovieState {
    data class RenderLoading(val isLoading: Boolean) : MovieState()
    data class RenderMovies(val data: List<MovieModel>) : MovieState()
    data class RenderError(val error: String) : MovieState()
}