package com.didik.moflix.data.datasource

import android.content.Context
import com.didik.moflix.app.MoflixApp
import com.didik.moflix.data.response.SeriesListResponse
import com.didik.moflix.data.response.SeriesResponse
import com.didik.moflix.utils.helpers.JSONHelper
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.*

class SeriesLocalDataSourceImplTest : ShouldSpec({

    lateinit var seriesLocalDataSourceImpl: SeriesLocalDataSourceImpl

    beforeTest {
        mockkObject(MoflixApp)
        mockkObject(JSONHelper)

        seriesLocalDataSourceImpl = spyk(SeriesLocalDataSourceImpl())
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
            seriesLocalDataSourceImpl.getSeries() shouldBe mockSeries
            verify(exactly = 1) { JSONHelper.readSeriesJson() }
        }
    }

})