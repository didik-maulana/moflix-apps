package com.didik.moflix.data.movies.mapper

import com.didik.moflix.data.movies.datasource.local.entities.MovieEntity
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
        movieMapper = MovieMapper()
    }

    afterTest {
        unmockkAll()
    }

    context("map response to domain") {
        beforeEach {
            mockkObject(ImageHelper)
            mockkStatic("com.didik.moflix.utils.extensions.StringExtensionKt")
            mockkStatic("com.didik.moflix.utils.extensions.FloatExtensionKt")
        }

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

            // When
            val result = movieMapper.mapResponseToDomain(fakeMovieResponse)

            // Then
            with(result) {
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

        should("mapping list movie response to list movie model") {
            // Given
            val fakeMovieResponseList: List<MovieResponse> = mockk(relaxed = true)

            // When
            val result = movieMapper.mapResponseToListDomain(fakeMovieResponseList)

            // Then
            result shouldBe instanceOf<List<MovieModel>>()
        }
    }

    context("map entity to domain") {
        // Given
        val fakeMovieEntity = MovieEntity(
            id = Faker.int,
            title = Faker.string,
            thumbnailUrl = Faker.string,
            releaseDate = Faker.string,
            rating = Faker.float,
            ratingText = Faker.numericalString,
            createdAt = Faker.long
        )

        should("mapping movie entity to movie model") {
            // When
            val result = movieMapper.mapEntityToDomain(fakeMovieEntity)

            // Then
            with(result) {
                id shouldBe fakeMovieEntity.id
                title shouldBe fakeMovieEntity.title
                backdropUrl shouldBe ""
                thumbnailUrl shouldBe fakeMovieEntity.thumbnailUrl
                releaseDate shouldBe fakeMovieEntity.releaseDate
                rating shouldBe fakeMovieEntity.rating
                ratingText shouldBe fakeMovieEntity.ratingText
                overview shouldBe ""
                cast shouldBe emptyList()
            }
        }

        should("get mapper entity to domain and return correct movie") {
            // Given
            val fakeMovie = MovieModel(
                id = fakeMovieEntity.id,
                title = fakeMovieEntity.title,
                backdropUrl = "",
                thumbnailUrl = fakeMovieEntity.thumbnailUrl,
                releaseDate = fakeMovieEntity.releaseDate,
                rating = fakeMovieEntity.rating,
                ratingText = fakeMovieEntity.ratingText,
                overview = "",
                cast = emptyList()
            )

            // When
            val result = movieMapper.getMapperEntityToDomain().apply(fakeMovieEntity)

            // Then
            result shouldBe fakeMovie
        }
    }

    context("map domain to entity") {
        should("mapping movie model into movie entity") {
            // Given
            val fakeMovie = MovieModel(
                id = Faker.int,
                title = Faker.string,
                backdropUrl = Faker.string,
                thumbnailUrl = Faker.string,
                releaseDate = Faker.string,
                rating = Faker.float,
                ratingText = Faker.string,
                overview = Faker.string,
                cast = emptyList()
            )

            // When
            val result = movieMapper.mapDomainToEntity(fakeMovie)

            // Then
            with(result) {
                id shouldBe fakeMovie.id
                title shouldBe fakeMovie.title
                thumbnailUrl shouldBe fakeMovie.thumbnailUrl
                releaseDate shouldBe fakeMovie.releaseDate
                rating shouldBe fakeMovie.rating
                ratingText shouldBe fakeMovie.ratingText
                createdAt shouldNotBe null
            }
        }
    }

})