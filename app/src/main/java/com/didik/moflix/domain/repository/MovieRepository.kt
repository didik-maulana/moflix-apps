package com.didik.moflix.domain.repository

import com.didik.moflix.domain.entity.Movie

interface MovieRepository {
    suspend fun getMovies(): List<Movie>
}