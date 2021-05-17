package com.didik.moflix.domain.usecase

import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.domain.repository.SeriesRepository
import com.didik.moflix.utils.state.ResultState
import javax.inject.Inject

class SeriesUseCase @Inject constructor(private val repository: SeriesRepository) {

    suspend fun getSeries(): ResultState<List<MovieModel>> {
        return repository.getSeries()
    }

    suspend fun getSeriesDetail(seriesId: Int): ResultState<MovieModel> {
        return repository.getSeriesDetail(seriesId)
    }
}