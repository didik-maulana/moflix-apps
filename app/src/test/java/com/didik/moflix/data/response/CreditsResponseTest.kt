package com.didik.moflix.data.response

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import io.mockk.unmockkAll

class CreditsResponseTest : ShouldSpec({

    val fakeCast: List<CastResponse> = mockk()
    lateinit var creditsResponse: CreditsResponse

    beforeTest {
        creditsResponse = CreditsResponse(fakeCast)
    }

    afterTest {
        unmockkAll()
    }

    should("return correct cast value when injected") {
        creditsResponse.cast shouldBe fakeCast
    }

})