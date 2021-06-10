package com.didik.moflix.domain.usecase

import androidx.paging.DataSource
import androidx.sqlite.db.SupportSQLiteQuery
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.domain.repository.SeriesRepository
import com.didik.moflix.helpers.Faker
import com.didik.moflix.utils.dispatcher.DispatchersProvider
import com.didik.moflix.utils.dispatcher.TestDispatchers
import com.didik.moflix.utils.state.ResultState
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.*

class SeriesUseCaseTest : ShouldSpec({

    val repository: SeriesRepository = mockk()
    val dispatchers: DispatchersProvider = TestDispatchers()
    lateinit var seriesUseCase: SeriesUseCase

    beforeTest {
        seriesUseCase = SeriesUseCase(
            repository = repository,
            dispatchers = dispatchers
        )
    }

    val fakeSeriesId = Faker.int
    val fakeSeries: MovieModel = mockk()

    afterTest {
        unmockkAll()
    }

    context("get series") {
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

    context("get series detail") {
        should("get series detail from repository and return result state of movie model") {
            // Given
            val fakeResult: ResultState<MovieModel> = mockk()

            coEvery { repository.getSeriesDetail(fakeSeriesId) } returns fakeResult

            // When
            val result = seriesUseCase.getSeriesDetail(fakeSeriesId)

            // Then
            result shouldBe fakeResult
            coVerify(exactly = 1) { repository.getSeriesDetail(fakeSeriesId) }
        }
    }

    context("get favorite series") {
        should("get favorite series from repository and return data source factory") {
            // Given
            val fakeQuery: SupportSQLiteQuery = mockk()
            val fakeFavoriteDataFactory: DataSource.Factory<Int, MovieModel> = mockk()

            coEvery { repository.getFavoriteSeries(fakeQuery) } returns fakeFavoriteDataFactory

            // When
            val result = seriesUseCase.getFavoriteSeries(fakeQuery)

            result shouldBe fakeFavoriteDataFactory
            coVerify(exactly = 1) { repository.getFavoriteSeries(fakeQuery) }
        }
    }

    context("check favorite series") {
        should("call check favorite series from repository and return Boolean") {
            // Given
            val fakeIsFavorite = Faker.boolean

            coEvery { repository.checkFavoriteSeries(fakeSeriesId) } returns fakeIsFavorite

            // When
            val isFavorite = seriesUseCase.checkFavoriteSeries(fakeSeriesId)

            // Then
            isFavorite shouldBe fakeIsFavorite
            coVerify(exactly = 1) { repository.checkFavoriteSeries(fakeSeriesId) }
        }
    }

    context("insert favorite series") {
        should("call insert favorite series from repository") {
            // Given
            coEvery { repository.insertFavoriteSeries(fakeSeries) } just runs

            // When
            seriesUseCase.insertFavoriteSeries(fakeSeries)

            // Then
            coVerify(exactly = 1) { repository.insertFavoriteSeries(fakeSeries) }
        }
    }

    context("delete favorite series") {
        should("call delete favorite series from repository") {
            // Given
            coEvery { repository.deleteFavoriteSeries(fakeSeries) } just runs

            // When
            seriesUseCase.deleteFavoriteSeries(fakeSeries)

            // Then
            coVerify(exactly = 1) { repository.deleteFavoriteSeries(fakeSeries) }
        }
    }

})