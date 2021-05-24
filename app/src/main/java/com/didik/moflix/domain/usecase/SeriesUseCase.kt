package com.didik.moflix.domain.usecase

import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.domain.repository.SeriesRepository
import com.didik.moflix.utils.dispatcher.DispatchersProvider
import com.didik.moflix.utils.state.ResultState
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SeriesUseCase @Inject constructor(
    private val repository: SeriesRepository,
    private val dispatchers: DispatchersProvider
) {

    suspend fun getSeries(): ResultState<List<MovieModel>> {
        return withContext(dispatchers.io) {
            repository.getSeries()
        }
    }

    suspend fun getSeriesDetail(seriesId: Int): ResultState<MovieModel> {
        return withContext(dispatchers.io) {
            repository.getSeriesDetail(seriesId)
        }
    }
}