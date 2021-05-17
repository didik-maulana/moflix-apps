package com.didik.moflix.data.series.repository

import com.didik.moflix.data.series.datasource.remote.SeriesRemoteDataSource
import com.didik.moflix.data.series.mapper.SeriesMapper
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.domain.repository.SeriesRepository
import com.didik.moflix.utils.state.ResultState
import javax.inject.Inject

class SeriesRepositoryImpl @Inject constructor(
    private val remoteDataSource: SeriesRemoteDataSource,
    private val mapper: SeriesMapper
) : SeriesRepository {

    override suspend fun getSeries(): ResultState<List<MovieModel>> {
        return try {
            val response = remoteDataSource.getSeries()
            if (response.isSuccessful) {
                val results = response.body()?.results.orEmpty()
                val seriesList = mapper.mapToListDomain(results)
                ResultState.Success(seriesList)
            } else {
                ResultState.Failure(response.message())
            }
        } catch (exception: Exception) {
            ResultState.Failure(exception.message.orEmpty())
        }
    }

    override suspend fun getSeriesDetail(seriesId: Int): ResultState<MovieModel> {
        return try {
            val response = remoteDataSource.getSeriesDetail(seriesId)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                val seriesDetail = mapper.mapToDomain(result)
                ResultState.Success(seriesDetail)
            } else {
                ResultState.Failure(response.message())
            }
        } catch (exception: Exception) {
            ResultState.Failure(exception.message.orEmpty())
        }
    }
}