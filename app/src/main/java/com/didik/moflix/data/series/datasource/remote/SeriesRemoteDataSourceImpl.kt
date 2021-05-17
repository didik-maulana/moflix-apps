package com.didik.moflix.data.series.datasource.remote

import com.didik.moflix.data.routes.ApiServices
import com.didik.moflix.data.series.datasource.remote.response.SeriesListResponse
import com.didik.moflix.data.series.datasource.remote.response.SeriesResponse
import retrofit2.Response
import javax.inject.Inject

class SeriesRemoteDataSourceImpl @Inject constructor(
    private val apiServices: ApiServices
) : SeriesRemoteDataSource {

    override suspend fun getSeries(): Response<SeriesListResponse> {
        return apiServices.getPopularSeries()
    }

    override suspend fun getSeriesDetail(seriesId: Int): Response<SeriesResponse> {
        return apiServices.getSeriesDetail(seriesId)
    }
}