package com.didik.moflix.data.repository

import com.didik.moflix.data.datasource.SeriesLocalDataSource
import com.didik.moflix.data.mapper.SeriesMapper
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.domain.repository.SeriesRepository
import javax.inject.Inject

class SeriesRepositoryImpl @Inject constructor(
    private val localDataSource: SeriesLocalDataSource,
    private val mapper: SeriesMapper
) : SeriesRepository {

    override suspend fun getSeries(): List<MovieModel> {
        val seriesList = localDataSource.getSeries()
        return mapper.mapToListDomain(seriesList)
    }
}