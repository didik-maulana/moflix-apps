package com.didik.moflix.presentation.features.movies.list

import androidx.lifecycle.Observer
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.domain.usecase.MovieUseCase
import com.didik.moflix.helpers.Faker
import com.didik.moflix.helpers.InstantExecutorListener
import com.didik.moflix.utils.state.ResultState
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.*

class MovieViewModelTest : ShouldSpec({

    val movieUseCase: MovieUseCase = mockk()
    lateinit var movieViewModel: MovieViewModel

    listeners(InstantExecutorListener())

    beforeTest {
        movieViewModel = MovieViewModel(movieUseCase)
    }

    afterTest {
        unmockkAll()
    }

    context("get movies") {
        should("movies value is correct when result state is success") {
            // Given
            val fakeMovies: List<MovieModel> = mockk()
            val moviesObserver: Observer<List<MovieModel>> = mockk()

            every { moviesObserver.onChanged(any()) } just runs
            coEvery { movieUseCase.getMovies() } returns ResultState.Success(fakeMovies)

            // When
            movieViewModel.getMovies()
            movieViewModel.movies.observeForever(moviesObserver)

            // Then
            movieViewModel.isLoading.value shouldBe false
            movieViewModel.movies.value shouldBe fakeMovies
            coVerify(exactly = 1) { movieUseCase.getMovies() }
        }

        should("error value is correct when result state is failure") {
            // Given
            val fakeError = Faker.string
            val errorObserver: Observer<String> = mockk()

            every { errorObserver.onChanged(any()) } just runs
            coEvery { movieUseCase.getMovies() } returns ResultState.Failure(fakeError)

            // When
            movieViewModel.getMovies()
            movieViewModel.error.observeForever(errorObserver)

            // Then
            movieViewModel.isLoading.value shouldBe false
            movieViewModel.error.value shouldBe fakeError
        }
    }

})