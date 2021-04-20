package com.didik.moflix.domain.repository

import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.utils.state.ResultState

interface MovieRepository {
    suspend fun getMovies(): ResultState<List<MovieModel>>
}