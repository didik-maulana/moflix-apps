package com.didik.moflix.data.series.datasource.remote

import com.didik.moflix.data.series.datasource.remote.response.SeriesResponse
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll

class SeriesRemoteDataSourceTest : ShouldSpec({

    lateinit var seriesRemoteDataSource: SeriesRemoteDataSource

    beforeTest {
        seriesRemoteDataSource = mockk()
    }

    afterTest {
        unmockkAll()
    }

    context("getSeries") {
        should("return list of series response") {
            // Given
            val mockSeriesResponseList: List<SeriesResponse> = mockk()

            coEvery { seriesRemoteDataSource.getSeries() } returns mockSeriesResponseList

            // Then
            seriesRemoteDataSource.getSeries() shouldBe mockSeriesResponseList
        }
    }

})