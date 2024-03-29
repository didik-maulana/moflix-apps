package com.didik.moflix.presentation.features.series.list

import androidx.lifecycle.Observer
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.domain.usecase.SeriesUseCase
import com.didik.moflix.helpers.Faker
import com.didik.moflix.helpers.InstantExecutorListener
import com.didik.moflix.utils.state.ResultState
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.*

class SeriesViewModelTest : ShouldSpec({

    val seriesUseCase: SeriesUseCase = mockk()
    lateinit var seriesViewModel: SeriesViewModel

    listeners(InstantExecutorListener())

    beforeTest {
        seriesViewModel = SeriesViewModel(seriesUseCase)
    }

    afterTest {
        unmockkAll()
    }

    context("get series") {
        should("series value is correct when result state is success") {
            // Given
            val fakeSeries: List<MovieModel> = mockk()
            val seriesObserver: Observer<List<MovieModel>> = mockk()

            every { seriesObserver.onChanged(any()) } just runs
            coEvery { seriesUseCase.getSeries() } returns ResultState.Success(fakeSeries)

            // When
            seriesViewModel.getSeries()
            seriesViewModel.series.observeForever(seriesObserver)

            // Then
            seriesViewModel.isLoading.value shouldBe false
            seriesViewModel.series.value shouldBe fakeSeries
            coVerify(exactly = 1) { seriesUseCase.getSeries() }
        }

        should("error value is correct when result state is failure") {
            // Given
            val fakeError = Faker.string
            val errorObserver: Observer<String> = mockk()

            every { errorObserver.onChanged(any()) } just runs
            coEvery { seriesUseCase.getSeries() } returns ResultState.Failure(fakeError)

            // When
            seriesViewModel.getSeries()
            seriesViewModel.error.observeForever(errorObserver)

            // Then
            seriesViewModel.isLoading.value shouldBe false
            seriesViewModel.error.value shouldBe fakeError
        }
    }

})