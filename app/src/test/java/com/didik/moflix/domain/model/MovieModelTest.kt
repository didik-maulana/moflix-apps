package com.didik.moflix.domain.model

import com.didik.moflix.helpers.Faker
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.spyk

class MovieModelTest : ShouldSpec({

    val fakeTitle = Faker.string
    val fakeBackdropUrl = Faker.string
    val fakeThumbnailUrl = Faker.string
    val fakeReleaseDate = Faker.string
    val fakeRating = Faker.float
    val fakeRatingText = Faker.numericalString
    val fakeOverview = Faker.string

    lateinit var movieModel: MovieModel

    beforeTest {
        movieModel = spyk(
            MovieModel(
                title = fakeTitle,
                backdropUrl = fakeBackdropUrl,
                thumbnailUrl = fakeThumbnailUrl,
                releaseDate = fakeReleaseDate,
                rating = fakeRating,
                ratingText = fakeRatingText,
                overview = fakeOverview
            )
        )
    }

    should("return correct values") {
        with(movieModel) {
            title shouldBe fakeTitle
            backdropUrl shouldBe fakeBackdropUrl
            thumbnailUrl shouldBe fakeThumbnailUrl
            releaseDate shouldBe fakeReleaseDate
            rating shouldBe fakeRating
            ratingText shouldBe fakeRatingText
            overview shouldBe fakeOverview
        }
    }

})