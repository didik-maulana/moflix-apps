package com.didik.moflix.data.mapper

import com.didik.moflix.data.response.SeriesResponse
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.helpers.Faker
import com.didik.moflix.network.helper.ImageHelper
import com.didik.moflix.utils.extensions.formatReleaseDate
import com.didik.moflix.utils.extensions.toRatingFormat
import com.didik.moflix.utils.extensions.toRatingText
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.instanceOf
import io.mockk.*

class SeriesMapperTest : ShouldSpec({

    lateinit var seriesMapper: SeriesMapper

    beforeTest {
        mockkObject(ImageHelper)
        mockkStatic("com.didik.moflix.utils.extensions.StringExtensionKt")
        mockkStatic("com.didik.moflix.utils.extensions.FloatExtensionKt")

        seriesMapper = SeriesMapper()
    }

    afterTest {
        unmockkAll()
    }

    context("mapToDomain") {
        should("mapping series response to movie model") {
            // Given
            val fakeImageUrl = Faker.string
            val fakeSeriesResponse = spyk(
                SeriesResponse(
                    name = Faker.string,
                    backdropPath = Faker.string,
                    posterPath = Faker.string,
                    firstAirDate = Faker.string,
                    voteAverage = Faker.float,
                    overview = Faker.string
                )
            )

            every { ImageHelper.getImageURL(any(), any()) } returns fakeImageUrl

            // Then
            with(seriesMapper.mapToDomain(fakeSeriesResponse)) {
                title shouldBe fakeSeriesResponse.name
                backdropUrl shouldBe fakeImageUrl
                thumbnailUrl shouldBe fakeImageUrl
                releaseDate shouldBe fakeSeriesResponse.firstAirDate.formatReleaseDate()
                rating shouldBe fakeSeriesResponse.voteAverage.toRatingFormat()
                ratingText shouldBe fakeSeriesResponse.voteAverage.toRatingText()
                overview shouldBe overview
            }
        }
    }

    context("mapToListDomain") {
        should("mapping list series response to list movie model") {
            // Given
            val fakeSeriesResponseList: List<SeriesResponse> = mockk(relaxed = true)

            // Then
            seriesMapper.mapToListDomain(fakeSeriesResponseList) shouldBe instanceOf<List<MovieModel>>()
        }
    }

})