package com.didik.moflix.data.movies.mapper

import com.didik.moflix.data.movies.datasource.remote.response.MovieResponse
import com.didik.moflix.data.response.CastResponse
import com.didik.moflix.data.response.CreditsResponse
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.helpers.Faker
import com.didik.moflix.network.helper.ImageHelper
import com.didik.moflix.utils.extensions.formatReleaseDate
import com.didik.moflix.utils.extensions.toRatingFormat
import com.didik.moflix.utils.extensions.toRatingText
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
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

    context("mapResponseToDomain") {
        should("mapping movie response to movie model") {
            // Given
            val fakeImageUrl = Faker.string
            val fakeCastResponse = CastResponse(
                name = Faker.string,
                profilePath = Faker.string,
                character = Faker.string
            )
            val fakeCreditsResponse = CreditsResponse(
                cast = listOf(fakeCastResponse)
            )
            val fakeMovieResponse = MovieResponse(
                title = Faker.string,
                backdropPath = Faker.string,
                posterPath = Faker.string,
                releaseDate = Faker.string,
                voteAverage = Faker.float,
                overview = Faker.string,
                credits = fakeCreditsResponse
            )

            every { ImageHelper.getImageURL(any(), any()) } returns fakeImageUrl

            // Then
            with(movieMapper.mapResponseToDomain(fakeMovieResponse)) {
                title shouldBe fakeMovieResponse.title
                backdropUrl shouldBe fakeImageUrl
                thumbnailUrl shouldBe fakeImageUrl
                releaseDate shouldBe fakeMovieResponse.releaseDate.formatReleaseDate()
                rating shouldBe fakeMovieResponse.voteAverage.toRatingFormat()
                ratingText shouldBe fakeMovieResponse.voteAverage.toRatingText()
                overview shouldBe fakeMovieResponse.overview
                cast shouldNotBe null
            }
        }
    }

    context("mapResponseToListDomain") {
        should("mapping list movie response to list movie model") {
            // Given
            val fakeMovieResponseList: List<MovieResponse> = mockk(relaxed = true)

            // Then
            movieMapper.mapResponseToListDomain(fakeMovieResponseList) shouldBe instanceOf<List<MovieModel>>()
        }
    }

})