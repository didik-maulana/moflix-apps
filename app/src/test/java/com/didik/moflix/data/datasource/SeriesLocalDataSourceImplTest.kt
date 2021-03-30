package com.didik.moflix.data.datasource

import android.content.Context
import com.didik.moflix.app.AppProvider
import com.didik.moflix.data.response.SeriesListResponse
import com.didik.moflix.data.response.SeriesResponse
import com.didik.moflix.utils.helpers.JsonHelper
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.*

class SeriesLocalDataSourceImplTest : ShouldSpec({

    lateinit var seriesLocalDataSourceImpl: SeriesLocalDataSourceImpl

    beforeTest {
        mockkObject(AppProvider)
        mockkObject(JsonHelper)

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

            every { AppProvider.context } returns mockContext
            every { JsonHelper.readSeriesJson(mockContext) } returns mockSeriesListResponse

            // Then
            seriesLocalDataSourceImpl.getSeries() shouldBe mockSeries
            verify(exactly = 1) { JsonHelper.readSeriesJson(mockContext) }
        }
    }

})