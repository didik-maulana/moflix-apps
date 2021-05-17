package com.didik.moflix.data.series.datasource.remote

import android.content.Context
import com.didik.moflix.app.MoflixApp
import com.didik.moflix.data.series.datasource.remote.response.SeriesListResponse
import com.didik.moflix.data.series.datasource.remote.response.SeriesResponse
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.*

class SeriesRemoteDataSourceImplTest : ShouldSpec({

    lateinit var seriesRemoteDataSourceImpl: SeriesRemoteDataSourceImpl

    beforeTest {
        mockkObject(MoflixApp)
        mockkObject(JSONHelper)

        seriesRemoteDataSourceImpl = spyk(SeriesRemoteDataSourceImpl())
    }

    afterTest {
        unmockkAll()
    }

    context("getSeries") {
        should("fetch from local json and return list of series model") {
            // Given
            val mockContext: Context = mockk()
            val mockSeries: List<SeriesResponse> = mockk()
            val mockSeriesListResponse = spyk(SeriesListResponse(mockSeries))

            every { MoflixApp.applicationContext } returns mockContext
            every { JSONHelper.readSeriesJson() } returns mockSeriesListResponse

            // Then
            seriesRemoteDataSourceImpl.getSeries() shouldBe mockSeries
            verify(exactly = 1) { JSONHelper.readSeriesJson() }
        }
    }

})