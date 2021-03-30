package com.didik.moflix.presentation.movies

import androidx.lifecycle.Observer
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.domain.usecase.GetMoviesUseCase
import com.didik.moflix.helpers.InstantExecutorListener
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.*

class MoviesViewModelTest : ShouldSpec({

    val mockGetMoviesUseCase: GetMoviesUseCase = mockk()
    lateinit var moviesViewModel: MoviesViewModel

    listeners(InstantExecutorListener())

    beforeTest {
        moviesViewModel = spyk(MoviesViewModel(mockGetMoviesUseCase))
    }

    afterTest {
        unmockkAll()
    }

    context("getMovies") {
        should("get movies from use case and set into movie list") {
            // Given
            val mockMovieList: List<MovieModel> = mockk()
            val mockMovieListObserver: Observer<List<MovieModel>> = mockk()

            every { mockMovieListObserver.onChanged(any()) } just runs
            coEvery { mockGetMoviesUseCase.getMovies() } returns mockMovieList

            // When
            moviesViewModel.movieList.observeForever(mockMovieListObserver)
            moviesViewModel.getMovies()

            // Then
            moviesViewModel.movieList.value shouldBe mockMovieList
            coVerify(exactly = 1) { mockGetMoviesUseCase.getMovies() }
        }
    }

})