package com.didik.moflix.domain.repository

import com.didik.moflix.domain.model.MovieModel
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
        should("return list of movie model") {
            // Given
            val mockMovieList: List<MovieModel> = mockk()

            coEvery { movieRepository.getMovies() } returns mockMovieList

            // Then
            movieRepository.getMovies() shouldBe mockMovieList
        }
    }

})