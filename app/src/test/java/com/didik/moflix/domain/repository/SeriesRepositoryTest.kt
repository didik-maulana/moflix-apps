package com.didik.moflix.domain.repository

import com.didik.moflix.domain.model.MovieModel
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll

class SeriesRepositoryTest : ShouldSpec({

    lateinit var seriesRepository: SeriesRepository

    beforeTest {
        seriesRepository = mockk()
    }

    afterTest {
        unmockkAll()
    }

    context("getSeries") {
        should("return list of movie model") {
            // Given
            val mockSeriesList: List<MovieModel> = mockk()

            coEvery { seriesRepository.getSeries() } returns mockSeriesList

            // Then
            seriesRepository.getSeries() shouldBe mockSeriesList
        }
    }

})