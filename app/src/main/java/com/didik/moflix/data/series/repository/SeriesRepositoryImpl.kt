package com.didik.moflix.data.series.repository

import com.didik.moflix.data.series.datasource.remote.SeriesRemoteDataSource
import com.didik.moflix.data.series.mapper.SeriesMapper
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.domain.repository.SeriesRepository
import javax.inject.Inject

class SeriesRepositoryImpl @Inject constructor(
    private val remoteDataSource: SeriesRemoteDataSource,
    private val mapper: SeriesMapper
) : SeriesRepository {

    override suspend fun getSeries(): List<MovieModel> {
        val seriesList = remoteDataSource.getSeries()
        return mapper.mapToListDomain(seriesList)
    }
}