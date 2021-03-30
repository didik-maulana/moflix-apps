package com.didik.moflix.domain.repository

import com.didik.moflix.domain.model.MovieModel

interface MovieRepository {
    suspend fun getMovies(): List<MovieModel>
}