package com.didik.moflix.data.datasource

import com.didik.moflix.data.response.SeriesResponse
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll

class SeriesLocalDataSourceTest : ShouldSpec({

    lateinit var seriesLocalDataSource: SeriesLocalDataSource

    beforeTest {
        seriesLocalDataSource = mockk()
    }

    afterTest {
        unmockkAll()
    }

    context("getSeries") {
        should("return list of series response") {
            // Given
            val mockSeriesResponseList: List<SeriesResponse> = mockk()

            coEvery { seriesLocalDataSource.getSeries() } returns mockSeriesResponseList

            // Then
            seriesLocalDataSource.getSeries() shouldBe mockSeriesResponseList
        }
    }

})