package com.didik.moflix.data.series.datasource.remote.response

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import io.mockk.unmockkAll

class SeriesListResponseTest : ShouldSpec({

    val fakeResults: List<SeriesResponse> = mockk()
    lateinit var seriesListResponse: SeriesListResponse

    beforeTest {
        seriesListResponse = SeriesListResponse(fakeResults)
    }

    afterTest {
        unmockkAll()
    }

    should("return correct results value when injected") {
        seriesListResponse.results shouldBe fakeResults
    }

})