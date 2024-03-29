package com.didik.moflix.data.series.mapper

import com.didik.moflix.data.response.CastResponse
import com.didik.moflix.data.response.CreditsResponse
import com.didik.moflix.data.series.datasource.local.entities.SeriesEntity
import com.didik.moflix.data.series.datasource.remote.response.SeriesResponse
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

class SeriesMapperTest : ShouldSpec({

    lateinit var seriesMapper: SeriesMapper

    beforeTest {
        seriesMapper = SeriesMapper()
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

        should("mapping series response to movie model") {
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
            val fakeSeriesResponse = SeriesResponse(
                name = Faker.string,
                backdropPath = Faker.string,
                posterPath = Faker.string,
                firstAirDate = Faker.string,
                voteAverage = Faker.float,
                overview = Faker.string,
                credits = fakeCreditsResponse
            )

            every { ImageHelper.getImageURL(any(), any()) } returns fakeImageUrl

            // Then
            with(seriesMapper.mapResponseToDomain(fakeSeriesResponse)) {
                title shouldBe fakeSeriesResponse.name
                backdropUrl shouldBe fakeImageUrl
                thumbnailUrl shouldBe fakeImageUrl
                releaseDate shouldBe fakeSeriesResponse.firstAirDate.formatReleaseDate()
                rating shouldBe fakeSeriesResponse.voteAverage.toRatingFormat()
                ratingText shouldBe fakeSeriesResponse.voteAverage.toRatingText()
                overview shouldBe overview
            }
        }

        should("mapping list series response to list movie model") {
            // Given
            val fakeSeriesResponseList: List<SeriesResponse> = mockk(relaxed = true)

            // Then
            seriesMapper.mapResponseToListDomain(fakeSeriesResponseList) shouldBe instanceOf<List<MovieModel>>()
        }
    }

    context("map entity to domain") {
        // Given
        val fakeSeriesEntity = SeriesEntity(
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
            val result = seriesMapper.mapEntityToDomain(fakeSeriesEntity)

            // Then
            with(result) {
                id shouldBe fakeSeriesEntity.id
                title shouldBe fakeSeriesEntity.title
                backdropUrl shouldBe ""
                thumbnailUrl shouldBe fakeSeriesEntity.thumbnailUrl
                releaseDate shouldBe fakeSeriesEntity.releaseDate
                rating shouldBe fakeSeriesEntity.rating
                ratingText shouldBe fakeSeriesEntity.ratingText
                overview shouldBe ""
                cast shouldBe emptyList()
            }
        }

        should("get mapper entity to domain and return correct movie") {
            // Given
            val fakeSeries = MovieModel(
                id = fakeSeriesEntity.id,
                title = fakeSeriesEntity.title,
                backdropUrl = "",
                thumbnailUrl = fakeSeriesEntity.thumbnailUrl,
                releaseDate = fakeSeriesEntity.releaseDate,
                rating = fakeSeriesEntity.rating,
                ratingText = fakeSeriesEntity.ratingText,
                overview = "",
                cast = emptyList()
            )

            // When
            val result = seriesMapper.getMapperEntityToDomain().apply(fakeSeriesEntity)

            // Then
            result shouldBe fakeSeries
        }
    }

    context("map domain to entity") {
        should("mapping movie model into series entity") {
            // Given
            val fakeSeries = MovieModel(
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
            val result = seriesMapper.mapDomainToEntity(fakeSeries)

            // Then
            with(result) {
                id shouldBe fakeSeries.id
                title shouldBe fakeSeries.title
                thumbnailUrl shouldBe fakeSeries.thumbnailUrl
                releaseDate shouldBe fakeSeries.releaseDate
                rating shouldBe fakeSeries.rating
                ratingText shouldBe fakeSeries.ratingText
                createdAt shouldNotBe null
            }
        }
    }

})