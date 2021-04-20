package com.didik.moflix.presentation.movies

import androidx.lifecycle.Observer
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.domain.usecase.MovieUseCase
import com.didik.moflix.helpers.InstantExecutorListener
import io.kotest.core.spec.style.ShouldSpec
import io.mockk.*

class MovieViewModelTest : ShouldSpec({

    val mockMovieUseCase: MovieUseCase = mockk()
    lateinit var movieViewModel: MovieViewModel

    listeners(InstantExecutorListener())

    beforeTest {
        movieViewModel = spyk(MovieViewModel(mockMovieUseCase))
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
            coEvery { mockMovieUseCase.getMovies() } returns mockMovieList

            // When
            movieViewModel.movieList.observeForever(mockMovieListObserver)
            movieViewModel.getMovies()

            // Then
            movieViewModel.movieList.value shouldBe mockMovieList
            coVerify(exactly = 1) { mockMovieUseCase.getMovies() }
        }
    }

})