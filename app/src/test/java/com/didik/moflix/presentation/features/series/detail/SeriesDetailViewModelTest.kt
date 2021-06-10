package com.didik.moflix.presentation.features.series.detail

import androidx.lifecycle.Observer
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.domain.usecase.SeriesUseCase
import com.didik.moflix.helpers.Faker
import com.didik.moflix.helpers.InstantExecutorListener
import com.didik.moflix.utils.state.ResultState
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.*

class SeriesDetailViewModelTest : ShouldSpec({

    val seriesUseCase: SeriesUseCase = mockk()
    lateinit var viewModel: SeriesDetailViewModel

    listeners(InstantExecutorListener())

    beforeTest {
        viewModel = SeriesDetailViewModel(seriesUseCase)
    }

    afterTest {
        unmockkAll()
    }

    context("initial properties") {
        should("return default value correctly") {
            with(viewModel) {
                seriesId shouldBe 0
                isFirstLoad shouldBe true
            }
        }
    }

    context("load series detail") {
        val seriesObserver: Observer<MovieModel> = mockk()

        should("series value is correct when result state is success") {
            // Given
            val fakeSeries: MovieModel = mockk()

            every { seriesObserver.onChanged(any()) } just runs
            coEvery {
                seriesUseCase.getSeriesDetail(viewModel.seriesId)
            } returns ResultState.Success(fakeSeries)

            // When
            viewModel.loadSeriesDetail()
            viewModel.series.observeForever(seriesObserver)

            // Then
            viewModel.isLoading.value shouldBe false
            viewModel.series.value shouldBe fakeSeries
        }

        should("error value is correct when result state is failure") {
            // Given
            val fakeError = Faker.string

            every { seriesObserver.onChanged(any()) } just runs
            coEvery {
                seriesUseCase.getSeriesDetail(viewModel.seriesId)
            } returns ResultState.Failure(fakeError)

            // When
            viewModel.loadSeriesDetail()
            viewModel.series.observeForever(seriesObserver)

            // Then
            viewModel.isLoading.value shouldBe false
            viewModel.error.value shouldBe fakeError
        }
    }

    context("check favorite series") {
        should("is favorite series return correct value") {
            // Given
            val fakeIsFavorite = Faker.boolean
            val isFavoriteObserver: Observer<Boolean> = mockk()

            every { isFavoriteObserver.onChanged(any()) } just runs
            coEvery { seriesUseCase.checkFavoriteSeries(viewModel.seriesId) } returns fakeIsFavorite

            // When
            viewModel.checkFavoriteSeries()
            viewModel.isFavorite.observeForever(isFavoriteObserver)

            // Then
            viewModel.isFavorite.value shouldBe fakeIsFavorite
            coVerify(exactly = 1) { seriesUseCase.checkFavoriteSeries(viewModel.seriesId) }
        }
    }

    context("insert favorite") {
        should("call insert favorite series from series use case") {
            // Given
            coEvery { seriesUseCase.insertFavoriteSeries(viewModel.series.value) } just runs

            // When
            viewModel.insertSeries()

            // Then
            coVerify { seriesUseCase.insertFavoriteSeries(viewModel.series.value) }
        }
    }

    context("delete favorite") {
        should("call delete favorite series from series use case") {
            // Given
            coEvery { seriesUseCase.deleteFavoriteSeries(viewModel.series.value) } just runs

            // When
            viewModel.deleteSeries()

            // Then
            coVerify { seriesUseCase.deleteFavoriteSeries(viewModel.series.value) }
        }
    }

})