package com.didik.moflix.data.series.datasource.remote

import com.didik.moflix.data.series.datasource.remote.response.SeriesListResponse
import com.didik.moflix.data.series.datasource.remote.response.SeriesResponse
import com.didik.moflix.helpers.Faker
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import retrofit2.Response

class SeriesRemoteDataSourceTest : ShouldSpec({

    lateinit var seriesRemoteDataSource: SeriesRemoteDataSource

    beforeTest {
        seriesRemoteDataSource = mockk()
    }

    afterTest {
        unmockkAll()
    }

    context("getSeries") {
        should("return response of series list response") {
            // Given
            val fakeResponse: Response<SeriesListResponse> = mockk()

            coEvery { seriesRemoteDataSource.getSeries() } returns fakeResponse

            // Then
            seriesRemoteDataSource.getSeries() shouldBe fakeResponse
        }
    }

    context("getSeriesDetail") {
        should("return response of series response") {
            // Given
            val fakeSeriesId = Faker.int
            val fakeResponse: Response<SeriesResponse> = mockk()

            coEvery { seriesRemoteDataSource.getSeriesDetail(fakeSeriesId) } returns fakeResponse

            // Then
            seriesRemoteDataSource.getSeriesDetail(fakeSeriesId) shouldBe fakeResponse
        }
    }

})