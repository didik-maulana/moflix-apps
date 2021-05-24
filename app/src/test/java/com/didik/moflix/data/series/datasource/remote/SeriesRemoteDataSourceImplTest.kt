package com.didik.moflix.data.series.datasource.remote

import com.didik.moflix.data.routes.ApiServices
import com.didik.moflix.data.series.datasource.remote.response.SeriesListResponse
import com.didik.moflix.data.series.datasource.remote.response.SeriesResponse
import com.didik.moflix.helpers.Faker
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import retrofit2.Response

class SeriesRemoteDataSourceImplTest : ShouldSpec({

    val apiServices: ApiServices = mockk()
    lateinit var seriesRemoteDataSourceImpl: SeriesRemoteDataSourceImpl

    beforeTest {
        seriesRemoteDataSourceImpl = SeriesRemoteDataSourceImpl(apiServices)
    }

    afterTest {
        unmockkAll()
    }

    context("getSeries") {
        should("fetch from api services and return response of series list response") {
            // Given
            val fakeResponse: Response<SeriesListResponse> = mockk()

            coEvery { apiServices.getPopularSeries() } returns fakeResponse

            // When
            val result = seriesRemoteDataSourceImpl.getSeries()

            // Then
            result shouldBe fakeResponse
            coVerify(exactly = 1) { apiServices.getPopularSeries() }
        }
    }

    context("getSeriesDetail") {
        should("fetch from api services and return response of series response") {
            // Given
            val fakeSeriesId = Faker.int
            val fakeResponse: Response<SeriesResponse> = mockk()

            coEvery { apiServices.getSeriesDetail(fakeSeriesId) } returns fakeResponse

            // When
            val result = seriesRemoteDataSourceImpl.getSeriesDetail(fakeSeriesId)

            // Then
            result shouldBe fakeResponse
            coVerify(exactly = 1) { apiServices.getSeriesDetail(fakeSeriesId) }
        }
    }

})