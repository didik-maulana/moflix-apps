package com.didik.moflix.data.movies.datasource.remote.response

import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.unmockkAll

class MovieListResponseTest : ShouldSpec({

    val mockResults: List<MovieResponse> = mockk()

    lateinit var movieListResponse: MovieListResponse

    beforeTest {
        movieListResponse = spyk(MovieListResponse(mockResults))
    }

    afterTest {
        unmockkAll()
    }

    should("return correct results value when injected") {
        movieListResponse.results shouldBe mockResults
    }

})