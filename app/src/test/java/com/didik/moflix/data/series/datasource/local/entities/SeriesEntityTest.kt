package com.didik.moflix.data.series.datasource.local.entities

import com.didik.moflix.helpers.Faker
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class SeriesEntityTest : ShouldSpec({

    val fakeId = Faker.int
    val fakeTitle = Faker.string
    val fakeThumbnailUrl = Faker.string
    val fakeReleaseDate = Faker.string
    val fakeRating = Faker.float
    val fakeRatingText = Faker.numericalString
    val fakeCreatedAt = Faker.long
    lateinit var entity: SeriesEntity

    beforeTest {
        entity = SeriesEntity(
            id = fakeId,
            title = fakeTitle,
            thumbnailUrl = fakeThumbnailUrl,
            releaseDate = fakeReleaseDate,
            rating = fakeRating,
            ratingText = fakeRatingText,
            createdAt = fakeCreatedAt
        )
    }

    should("return series entity correct values") {
        with(entity) {
            id shouldBe fakeId
            title shouldBe fakeTitle
            thumbnailUrl shouldBe fakeThumbnailUrl
            releaseDate shouldBe fakeReleaseDate
            rating shouldBe fakeRating
            ratingText shouldBe fakeRatingText
            createdAt shouldBe fakeCreatedAt
        }
    }

})