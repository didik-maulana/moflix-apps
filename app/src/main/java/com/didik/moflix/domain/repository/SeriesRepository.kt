package com.didik.moflix.domain.repository

import com.didik.moflix.domain.entity.Movie

interface SeriesRepository {
    suspend fun getSeries(): List<Movie>
}