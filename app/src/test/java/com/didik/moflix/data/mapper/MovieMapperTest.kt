package com.didik.moflix.data.mapper

import com.didik.moflix.data.response.MovieResponse
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

class MovieMapperTest : ShouldSpec({

    lateinit var movieMapper: MovieMapper

    beforeTest {
        mockkObject(ImageHelper)
        mockkStatic("com.didik.moflix.utils.extensions.StringExtensionKt")
        mockkStatic("com.didik.moflix.utils.extensions.FloatExtensionKt")

        movieMapper = MovieMapper()
    }

    afterTest {
        unmockkAll()
    }

    context("mapToDomain") {
        should("mapping movie response to movie model") {
            // Given
            val fakeImageUrl = Faker.string
            val fakeMovieResponse: MovieResponse = spyk(
                MovieResponse(
                    title = Faker.string,
                    backdropPath = Faker.string,
                    posterPath = Faker.string,
                    releaseDate = Faker.string,
                    voteAverage = Faker.float,
                    overview = Faker.string
                )
            )

            every { ImageHelper.getImageURL(any(), any()) } returns fakeImageUrl

            // Then
            with(movieMapper.mapToDomain(fakeMovieResponse)) {
                title shouldBe fakeMovieResponse.title
                backdropUrl shouldBe fakeImageUrl
                thumbnailUrl shouldBe fakeImageUrl
                releaseDate shouldBe fakeMovieResponse.releaseDate.formatReleaseDate()
                rating shouldBe fakeMovieResponse.voteAverage.toRatingFormat()
                ratingText shouldBe fakeMovieResponse.voteAverage.toRatingText()
                overview shouldBe fakeMovieResponse.overview
            }
        }
    }

    context("mapToListDomain") {
        should("mapping list movie response to list movie model") {
            // Given
            val fakeMovieResponseList: List<MovieResponse> = mockk(relaxed = true)

            // Then
            movieMapper.mapToListDomain(fakeMovieResponseList) shouldBe instanceOf<List<MovieModel>>()
        }
    }

})