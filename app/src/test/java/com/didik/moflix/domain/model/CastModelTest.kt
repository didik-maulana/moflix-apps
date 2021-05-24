package com.didik.moflix.domain.model

import com.didik.moflix.helpers.Faker
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe

class CastModelTest : ShouldSpec({

    val fakePhotoUrl = Faker.string
    val fakeName = Faker.string
    val fakeCharacter = Faker.string
    lateinit var castModel: CastModel

    beforeTest {
        castModel = CastModel(
            photoUrl = fakePhotoUrl,
            name = fakeName,
            character = fakeCharacter
        )
    }

    should("return correct cast model values") {
        with(castModel) {
            photoUrl shouldBe fakePhotoUrl
            name shouldBe fakeName
            character shouldBe fakeCharacter
        }
    }

})