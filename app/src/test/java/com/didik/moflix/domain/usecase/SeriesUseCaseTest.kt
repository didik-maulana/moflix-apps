package com.didik.moflix.domain.usecase

import com.didik.moflix.data.series.repository.SeriesRepositoryImpl
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.helpers.Faker
import com.didik.moflix.utils.state.ResultState
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll

class SeriesUseCaseTest : ShouldSpec({

    val repository: SeriesRepositoryImpl = mockk()
    lateinit var seriesUseCase: SeriesUseCase

    beforeTest {
        seriesUseCase = SeriesUseCase(repository)
    }

    afterTest {
        unmockkAll()
    }

    context("getSeries") {
        should("get series from repository and return result state of list movie model") {
            // Given
            val fakeResult: ResultState<List<MovieModel>> = mockk()

            coEvery { repository.getSeries() } returns fakeResult

            // When
            val result = seriesUseCase.getSeries()

            // Then
            result shouldBe fakeResult
            coVerify(exactly = 1) { repository.getSeries() }
        }
    }

    context("getSeriesDetail") {
        should("get series from repository and return result state of movie model") {
            // Given
            val fakeSeriesId = Faker.int
            val fakeResult: ResultState<MovieModel> = mockk()

            coEvery { repository.getSeriesDetail(fakeSeriesId) } returns fakeResult

            // When
            val result = seriesUseCase.getSeriesDetail(fakeSeriesId)

            // Then
            result shouldBe fakeResult
            coVerify(exactly = 1) { repository.getSeriesDetail(fakeSeriesId) }
        }
    }

})