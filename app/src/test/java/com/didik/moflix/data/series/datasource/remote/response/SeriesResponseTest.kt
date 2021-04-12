package com.didik.moflix.data.series.datasource.remote.response

import com.didik.moflix.helpers.Faker
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.spyk

class SeriesResponseTest : ShouldSpec({

    val fakeName = Faker.string
    val fakeBackdropPath = Faker.string
    val fakePosterPath = Faker.string
    val fakerFirstAirDate = Faker.string
    val fakeVoteAverage = Faker.float
    val fakeOverview = Faker.string

    lateinit var seriesResponse: SeriesResponse

    beforeTest {
        seriesResponse = spyk(
            SeriesResponse(
                name = fakeName,
                backdropPath = fakeBackdropPath,
                posterPath = fakePosterPath,
                firstAirDate = fakerFirstAirDate,
                voteAverage = fakeVoteAverage,
                overview = fakeOverview
            )
        )
    }

    should("return correct values") {
        with(seriesResponse) {
            name shouldBe fakeName
            backdropPath shouldBe fakeBackdropPath
            posterPath shouldBe fakePosterPath
            firstAirDate shouldBe fakerFirstAirDate
            voteAverage shouldBe fakeVoteAverage
            overview shouldBe fakeOverview
        }
    }

})