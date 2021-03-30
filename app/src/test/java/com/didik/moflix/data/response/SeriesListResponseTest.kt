package com.didik.moflix.data.response

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.unmockkAll

class SeriesListResponseTest : ShouldSpec({

    val mockResults: List<SeriesResponse> = mockk()

    lateinit var seriesListResponse: SeriesListResponse

    beforeTest {
        seriesListResponse = spyk(SeriesListResponse(mockResults))
    }

    afterTest {
        unmockkAll()
    }

    should("return correct results value when injected") {
        seriesListResponse.results shouldBe mockResults
    }

})