package com.didik.moflix.domain.usecase

import com.didik.moflix.data.movies.repository.MovieRepositoryImpl
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.helpers.Faker
import com.didik.moflix.utils.state.ResultState
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll

class GetMoviesUseCaseTest : ShouldSpec({

    val repository: MovieRepositoryImpl = mockk()
    lateinit var movieUseCase: MovieUseCase

    beforeTest {
        movieUseCase = MovieUseCase(repository)
    }

    afterTest {
        unmockkAll()
    }

    context("getMovies") {
        should("get movies from repository and return result state of list movie model") {
            // Given
            val fakeResult: ResultState<List<MovieModel>> = mockk()

            coEvery { repository.getMovies() } returns fakeResult

            // When
            val result = movieUseCase.getMovies()

            // Then
            result shouldBe fakeResult
            coVerify(exactly = 1) { repository.getMovies() }
        }
    }

    context("getMoviesDetail") {
        should("get movies from repository and return result state of movie model") {
            // Given
            val fakeMovieId = Faker.int
            val fakeResult: ResultState<MovieModel> = mockk()

            coEvery { repository.getMovieDetail(fakeMovieId) } returns fakeResult

            // When
            val result = movieUseCase.getMovieDetail(fakeMovieId)

            // Then
            result shouldBe fakeResult
            coVerify(exactly = 1) { repository.getMovies() }
        }
    }

})