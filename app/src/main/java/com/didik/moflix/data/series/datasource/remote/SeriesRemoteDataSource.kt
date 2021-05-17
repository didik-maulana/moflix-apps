package com.didik.moflix.data.series.datasource.remote

import com.didik.moflix.data.series.datasource.remote.response.SeriesListResponse
import com.didik.moflix.data.series.datasource.remote.response.SeriesResponse
import retrofit2.Response

interface SeriesRemoteDataSource {
    suspend fun getSeries(): Response<SeriesListResponse>
    suspend fun getSeriesDetail(seriesId: Int): Response<SeriesResponse>
}