package com.didik.moflix.domain.repository

import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.helpers.Faker
import com.didik.moflix.utils.state.ResultState
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll

class MovieRepositoryTest : ShouldSpec({

    lateinit var movieRepository: MovieRepository

    beforeTest {
        movieRepository = mockk()
    }

    afterTest {
        unmockkAll()
    }

    context("getMovies") {
        should("return result state of list movie model") {
            // Given
            val fakeResult: ResultState<List<MovieModel>> = mockk()

            coEvery { movieRepository.getMovies() } returns fakeResult

            // When
            val result = movieRepository.getMovies()

            // Then
            result shouldBe fakeResult
        }
    }

    context("getMoviesDetail") {
        should("return result state of movie model") {
            // Given
            val fakeMovieId = Faker.int
            val fakeResult: ResultState<MovieModel> = mockk()

            coEvery { movieRepository.getMovieDetail(fakeMovieId) } returns fakeResult

            // When
            val result = movieRepository.getMovieDetail(fakeMovieId)

            // Then
            result shouldBe fakeResult
        }
    }

})