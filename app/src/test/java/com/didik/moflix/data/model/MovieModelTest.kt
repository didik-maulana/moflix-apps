package com.didik.moflix.data.model

import com.didik.moflix.base.shouldBe
import io.mockk.spyk
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

class MovieModelTest : Spek({
    describe("Movie Model") {
        val movieModel = spyk(MovieModel(
            title = "",
            backdropPath = "",
            posterPath = "",
            releaseDate = "",
            voteAverage = 0f,
            overview = ""
        ))

        it("should correct value") {
            with(movieModel) {
                title shouldBe ""
            }
        }
    }
})