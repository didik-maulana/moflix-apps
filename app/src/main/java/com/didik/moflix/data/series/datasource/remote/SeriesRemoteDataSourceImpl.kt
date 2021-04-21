package com.didik.moflix.data.series.datasource.remote

import com.didik.moflix.data.routes.ApiServices
import com.didik.moflix.data.series.datasource.remote.response.SeriesListResponse
import retrofit2.Response
import javax.inject.Inject

class SeriesRemoteDataSourceImpl @Inject constructor(
    private val apiServices: ApiServices
) : SeriesRemoteDataSource {

    override suspend fun getSeries(): Response<SeriesListResponse> {
        return apiServices.getPopularSeries()
    }
}