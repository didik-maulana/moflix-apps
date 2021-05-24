package com.didik.moflix.presentation.movies

import androidx.lifecycle.Observer
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.domain.usecase.MovieUseCase
import com.didik.moflix.helpers.InstantExecutorListener
import com.didik.moflix.utils.dispatcher.DispatchersProvider
import com.didik.moflix.utils.dispatcher.TestDispatchers
import com.didik.moflix.utils.state.ResultState
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.*

class MovieViewModelTest : ShouldSpec({

    val movieUseCase: MovieUseCase = mockk()
    val dispatcher: DispatchersProvider = TestDispatchers()
    lateinit var movieViewModel: MovieViewModel

    listeners(InstantExecutorListener())

    beforeTest {
        movieViewModel = MovieViewModel(
            movieUseCase = movieUseCase,
        )
    }

    afterTest {
        unmockkAll()
    }

    context("getMovies") {
        should("movie state is render data when result state is success") {
            // Given
            val fakeMovies: List<MovieModel> = listOf()
            val moviesObserver: Observer<List<MovieModel>> = mockk()

            every { moviesObserver.onChanged(any()) } just runs
            coEvery { movieUseCase.getMovies() } returns ResultState.Success(fakeMovies)

            // When
            movieViewModel.getMovies()
            movieViewModel.movies.observeForever(moviesObserver)

            // Then
            movieViewModel.movies.value shouldBe fakeMovies
            coVerify(exactly = 1) { movieUseCase.getMovies() }
        }
    }

})