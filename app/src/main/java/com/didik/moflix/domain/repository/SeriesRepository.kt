package com.didik.moflix.domain.repository

import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.utils.state.ResultState

interface SeriesRepository {
    suspend fun getSeries(): ResultState<List<MovieModel>>
}