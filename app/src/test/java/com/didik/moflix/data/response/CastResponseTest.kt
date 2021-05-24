package com.didik.moflix.data.response

import com.didik.moflix.helpers.Faker
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.unmockkAll

class CastResponseTest : ShouldSpec({

    val fakeName = Faker.string
    val fakeProfilePath = Faker.string
    val fakeCharacter = Faker.string
    lateinit var castResponse: CastResponse

    beforeTest {
        castResponse = CastResponse(
            name = fakeName,
            profilePath = fakeProfilePath,
            character = fakeCharacter
        )
    }

    afterTest {
        unmockkAll()
    }

    should("return correct cast response value when injected") {
        with(castResponse) {
            name shouldBe fakeName
            profilePath shouldBe fakeProfilePath
            character shouldBe fakeCharacter
        }
    }

})