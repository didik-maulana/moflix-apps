package com.didik.moflix.domain.usecase

import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.domain.repository.SeriesRepository
import javax.inject.Inject

class GetSeriesUseCase @Inject constructor(private val repository: SeriesRepository) {

    suspend fun getSeries(): List<MovieModel> {
        return repository.getSeries()
    }
}