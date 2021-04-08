package com.didik.moflix.presentation.series

import androidx.lifecycle.Observer
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.domain.usecase.GetSeriesUseCase
import com.didik.moflix.helpers.InstantExecutorListener
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.*

class SeriesViewModelTest : ShouldSpec({

    val mockGetSeriesUseCase: GetSeriesUseCase = mockk()
    lateinit var seriesViewModel: SeriesViewModel

    listeners(InstantExecutorListener())

    beforeTest {
        seriesViewModel = spyk(SeriesViewModel(mockGetSeriesUseCase))
    }

    afterTest {
        unmockkAll()
    }

    context("getSeries") {
        should("get series from use case and set into series list") {
            // Given
            val mockSeriesList = listOf<MovieModel>(mockk())
            val mockMovieListObserver: Observer<List<MovieModel>> = mockk()

            every { mockMovieListObserver.onChanged(any()) } just runs
            coEvery { mockGetSeriesUseCase.getSeries() } returns mockSeriesList

            // When
            seriesViewModel.getSeries()

            // Then
            seriesViewModel.seriesList.value shouldBe mockSeriesList
            coVerify(exactly = 1) { mockGetSeriesUseCase.getSeries() }
        }
    }

})