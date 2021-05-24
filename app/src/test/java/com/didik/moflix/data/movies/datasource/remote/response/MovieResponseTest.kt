package com.didik.moflix.data.movies.datasource.remote.response

import com.didik.moflix.data.response.CreditsResponse
import com.didik.moflix.helpers.Faker
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk

class MovieResponseTest : ShouldSpec({

    val fakeId = Faker.int
    val fakeTitle = Faker.string
    val fakeBackdropPath = Faker.string
    val fakePosterPath = Faker.string
    val fakerReleaseDate = Faker.string
    val fakeVoteAverage = Faker.float
    val fakeOverview = Faker.string
    val fakeCredits: CreditsResponse = mockk()

    lateinit var movieResponse: MovieResponse

    beforeTest {
        movieResponse = MovieResponse(
            id = fakeId,
            title = fakeTitle,
            backdropPath = fakeBackdropPath,
            posterPath = fakePosterPath,
            releaseDate = fakerReleaseDate,
            voteAverage = fakeVoteAverage,
            overview = fakeOverview,
            credits = fakeCredits
        )
    }

    should("return correct values") {
        with(movieResponse) {
            id shouldBe fakeId
            title shouldBe fakeTitle
            backdropPath shouldBe fakeBackdropPath
            posterPath shouldBe fakePosterPath
            releaseDate shouldBe fakerReleaseDate
            voteAverage shouldBe fakeVoteAverage
            overview shouldBe fakeOverview
            credits shouldBe fakeCredits
        }
    }

})