package com.didik.moflix.data.series.repository

import androidx.paging.DataSource
import androidx.sqlite.db.SupportSQLiteQuery
import com.didik.moflix.data.series.datasource.local.SeriesLocalDataSource
import com.didik.moflix.data.series.datasource.remote.SeriesRemoteDataSource
import com.didik.moflix.data.series.mapper.SeriesMapper
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.domain.repository.SeriesRepository
import com.didik.moflix.utils.state.ResultState
import java.net.UnknownHostException
import javax.inject.Inject

class SeriesRepositoryImpl @Inject constructor(
    private val remoteDataSource: SeriesRemoteDataSource,
    private val localDataSource: SeriesLocalDataSource,
    private val mapper: SeriesMapper
) : SeriesRepository {

    override suspend fun getSeries(): ResultState<List<MovieModel>> {
        return try {
            val response = remoteDataSource.getSeries()
            return if (response.isSuccessful) {
                val results = response.body()?.results.orEmpty()
                val seriesList = mapper.mapResponseToListDomain(results)
                ResultState.Success(seriesList)
            } else {
                ResultState.Failure(response.message())
            }
        } catch (exception: UnknownHostException) {
            ResultState.Failure(exception.message.toString())
        }
    }

    override suspend fun getSeriesDetail(seriesId: Int): ResultState<MovieModel> {
        return try {
            val response = remoteDataSource.getSeriesDetail(seriesId)
            val result = response.body()
            if (response.isSuccessful && result != null) {
                val seriesDetail = mapper.mapResponseToDomain(result)
                ResultState.Success(seriesDetail)
            } else {
                ResultState.Failure(response.message())
            }
        } catch (exception: UnknownHostException) {
            ResultState.Failure(exception.message.toString())
        }
    }

    override suspend fun getFavoriteSeries(query: SupportSQLiteQuery): DataSource.Factory<Int, MovieModel> {
        return localDataSource.getSeries(query).map(mapper.getMapperEntityToDomain())
    }

    override suspend fun checkFavoriteSeries(seriesId: Int): Boolean {
        return localDataSource.getCountSeriesById(seriesId) > 0
    }

    override suspend fun insertFavoriteSeries(series: MovieModel) {
        val seriesEntity = mapper.mapDomainToEntity(series)
        localDataSource.insertSeries(seriesEntity)
    }

    override suspend fun deleteFavoriteSeries(series: MovieModel) {
        val seriesEntity = mapper.mapDomainToEntity(series)
        localDataSource.deleteSeries(seriesEntity)
    }
}