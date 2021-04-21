package com.didik.moflix.data.series.datasource.remote

import com.didik.moflix.data.series.datasource.remote.response.SeriesListResponse
import retrofit2.Response

interface SeriesRemoteDataSource {
    suspend fun getSeries(): Response<SeriesListResponse>
}