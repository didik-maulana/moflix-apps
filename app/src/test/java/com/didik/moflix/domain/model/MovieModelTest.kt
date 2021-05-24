package com.didik.moflix.domain.model

import com.didik.moflix.helpers.Faker
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.mockk

class MovieModelTest : ShouldSpec({

    val fakeId = Faker.int
    val fakeTitle = Faker.string
    val fakeBackdropUrl = Faker.string
    val fakeThumbnailUrl = Faker.string
    val fakeReleaseDate = Faker.string
    val fakeRating = Faker.float
    val fakeRatingText = Faker.numericalString
    val fakeOverview = Faker.string
    val fakeCast: List<CastModel> = mockk()
    lateinit var movieModel: MovieModel

    beforeTest {
        movieModel = MovieModel(
            id = fakeId,
            title = fakeTitle,
            backdropUrl = fakeBackdropUrl,
            thumbnailUrl = fakeThumbnailUrl,
            releaseDate = fakeReleaseDate,
            rating = fakeRating,
            ratingText = fakeRatingText,
            overview = fakeOverview,
            cast = fakeCast
        )
    }

    should("return correct values") {
        with(movieModel) {
            id shouldBe fakeId
            title shouldBe fakeTitle
            backdropUrl shouldBe fakeBackdropUrl
            thumbnailUrl shouldBe fakeThumbnailUrl
            releaseDate shouldBe fakeReleaseDate
            rating shouldBe fakeRating
            ratingText shouldBe fakeRatingText
            overview shouldBe fakeOverview
            cast shouldBe fakeCast
        }
    }

})