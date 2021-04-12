package com.didik.moflix.data.movies.datasource.remote.response

import com.didik.moflix.helpers.Faker
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.spyk

class MovieResponseTest : ShouldSpec({

    val fakeTitle = Faker.string
    val fakeBackdropPath = Faker.string
    val fakePosterPath = Faker.string
    val fakerReleaseDate = Faker.string
    val fakeVoteAverage = Faker.float
    val fakeOverview = Faker.string

    lateinit var movieResponse: MovieResponse

    beforeTest {
        movieResponse = spyk(
            MovieResponse(
                title = fakeTitle,
                backdropPath = fakeBackdropPath,
                posterPath = fakePosterPath,
                releaseDate = fakerReleaseDate,
                voteAverage = fakeVoteAverage,
                overview = fakeOverview
            )
        )
    }

    should("return correct values") {
        with(movieResponse) {
            title shouldBe fakeTitle
            backdropPath shouldBe fakeBackdropPath
            posterPath shouldBe fakePosterPath
            releaseDate shouldBe fakerReleaseDate
            voteAverage shouldBe fakeVoteAverage
            overview shouldBe fakeOverview
        }
    }

})