package com.didik.moflix.domain.repository

import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.helpers.Faker
import com.didik.moflix.utils.state.ResultState
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
        should("return result state of list movie model") {
            // Given
            val fakeResult: ResultState<List<MovieModel>> = mockk()

            coEvery { seriesRepository.getSeries() } returns fakeResult

            // When
            val result = seriesRepository.getSeries()

            // Then
            result shouldBe fakeResult
        }
    }

    context("getSeriesDetail") {
        should("return result state of movie model") {
            // Given
            val fakeSeriesId = Faker.int
            val fakeResult: ResultState<MovieModel> = mockk()

            coEvery { seriesRepository.getSeriesDetail(fakeSeriesId) } returns fakeResult

            // When
            val result = seriesRepository.getSeriesDetail(fakeSeriesId)

            // Then
            result shouldBe fakeResult
        }
    }

})