package com.didik.moflix.presentation.detail

import androidx.lifecycle.Observer
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.domain.usecase.MovieUseCase
import com.didik.moflix.domain.usecase.SeriesUseCase
import com.didik.moflix.helpers.Faker
import com.didik.moflix.helpers.InstantExecutorListener
import com.didik.moflix.utils.state.ResultState
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.*

class MovieDetailViewModelTest : ShouldSpec({

    val movieUseCase: MovieUseCase = mockk()
    val seriesUseCase: SeriesUseCase = mockk()
    lateinit var viewModel: MovieDetailViewModel

    listeners(InstantExecutorListener())

    beforeTest {
        viewModel = MovieDetailViewModel(
            movieUseCase = movieUseCase,
            seriesUseCase = seriesUseCase
        )
    }

    context("loadMovieDetail") {
        val fakeMovieId = Faker.int
        val movieObserver: Observer<MovieModel> = mockk()

        should("movie value is correct when result state is success") {
            // Given
            val fakeMovie: MovieModel = mockk()

            every { movieObserver.onChanged(any()) } just runs
            coEvery {
                movieUseCase.getMovieDetail(fakeMovieId)
            } returns ResultState.Success(fakeMovie)

            // When
            viewModel.loadMovieDetail(fakeMovieId)
            viewModel.movie.observeForever(movieObserver)

            // Then
            viewModel.isLoading.value shouldBe false
            viewModel.movie.value shouldBe fakeMovie
        }

        should("error value is correct when result state is failure") {
            // Given
            val fakeError = Faker.string

            every { movieObserver.onChanged(any()) } just runs
            coEvery {
                movieUseCase.getMovieDetail(fakeMovieId)
            } returns ResultState.Failure(fakeError)

            // When
            viewModel.loadMovieDetail(fakeMovieId)
            viewModel.movie.observeForever(movieObserver)

            // Then
            viewModel.isLoading.value shouldBe false
            viewModel.error.value shouldBe fakeError
        }
    }

    context("loadSeriesDetail") {
        val fakeSeriesId = Faker.int
        val movieObserver: Observer<MovieModel> = mockk()

        should("movie value is correct when result state is success") {
            // Given
            val fakeSeries: MovieModel = mockk()

            every { movieObserver.onChanged(any()) } just runs
            coEvery {
                seriesUseCase.getSeriesDetail(fakeSeriesId)
            } returns ResultState.Success(fakeSeries)

            // When
            viewModel.loadSeriesDetail(fakeSeriesId)
            viewModel.movie.observeForever(movieObserver)

            // Then
            viewModel.isLoading.value shouldBe false
            viewModel.movie.value shouldBe fakeSeries
        }

        should("error value is correct when result state is failure") {
            // Given
            val fakeError = Faker.string

            every { movieObserver.onChanged(any()) } just runs
            coEvery {
                seriesUseCase.getSeriesDetail(fakeSeriesId)
            } returns ResultState.Failure(fakeError)

            // When
            viewModel.loadSeriesDetail(fakeSeriesId)
            viewModel.movie.observeForever(movieObserver)

            // Then
            viewModel.isLoading.value shouldBe false
            viewModel.error.value shouldBe fakeError
        }
    }

})