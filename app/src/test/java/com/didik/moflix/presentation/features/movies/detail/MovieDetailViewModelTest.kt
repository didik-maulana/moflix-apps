package com.didik.moflix.presentation.features.movies.detail

import androidx.lifecycle.Observer
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.domain.usecase.MovieUseCase
import com.didik.moflix.helpers.Faker
import com.didik.moflix.helpers.InstantExecutorListener
import com.didik.moflix.utils.state.ResultState
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.*

class MovieDetailViewModelTest : ShouldSpec({

    val movieUseCase: MovieUseCase = mockk()
    lateinit var viewModel: MovieDetailViewModel

    listeners(InstantExecutorListener())

    beforeTest {
        viewModel = MovieDetailViewModel(movieUseCase)
    }

    context("initial properties") {
        should("return default value correctly") {
            with(viewModel) {
                movieId shouldBe 0
                isFirstLoad shouldBe true
            }
        }
    }

    context("load movie detail") {
        val movieObserver: Observer<MovieModel> = mockk()

        should("movie value is correct when result state is success") {
            // Given
            val fakeMovie: MovieModel = mockk()

            every { movieObserver.onChanged(any()) } just runs
            coEvery { movieUseCase.getMovieDetail(viewModel.movieId) } returns ResultState.Success(
                fakeMovie
            )

            // When
            viewModel.loadMovieDetail()
            viewModel.movie.observeForever(movieObserver)

            // Then
            viewModel.isLoading.value shouldBe false
            viewModel.movie.value shouldBe fakeMovie
        }

        should("error value is correct when result state is failure") {
            // Given
            val fakeError = Faker.string

            every { movieObserver.onChanged(any()) } just runs
            coEvery { movieUseCase.getMovieDetail(viewModel.movieId) } returns ResultState.Failure(
                fakeError
            )

            // When
            viewModel.loadMovieDetail()
            viewModel.movie.observeForever(movieObserver)

            // Then
            viewModel.isLoading.value shouldBe false
            viewModel.error.value shouldBe fakeError
        }
    }

    context("check favorite movie") {
        should("is favorite movie return correct value") {
            // Given
            val fakeIsFavorite = Faker.boolean
            val isFavoriteObserver: Observer<Boolean> = mockk()

            every { isFavoriteObserver.onChanged(any()) } just runs
            coEvery { movieUseCase.checkFavoriteMovie(viewModel.movieId) } returns fakeIsFavorite

            // When
            viewModel.checkFavoriteMovie()
            viewModel.isFavorite.observeForever(isFavoriteObserver)

            // Then
            viewModel.isFavorite.value shouldBe fakeIsFavorite
            coVerify(exactly = 1) { movieUseCase.checkFavoriteMovie(viewModel.movieId) }
        }
    }

    context("insert favorite") {
        should("call insert favorite movie from movie use case") {
            // Given
            coEvery { movieUseCase.insertFavoriteMovie(viewModel.movie.value) } just runs

            // When
            viewModel.insertMovie()

            // Then
            coVerify { movieUseCase.insertFavoriteMovie(viewModel.movie.value) }
        }
    }

    context("delete favorite") {
        should("call delete favorite movie from movie use case") {
            // Given
            coEvery { movieUseCase.deleteFavoriteMovie(viewModel.movie.value) } just runs

            // When
            viewModel.deleteMovie()

            // Then
            coVerify { movieUseCase.deleteFavoriteMovie(viewModel.movie.value) }
        }
    }

})