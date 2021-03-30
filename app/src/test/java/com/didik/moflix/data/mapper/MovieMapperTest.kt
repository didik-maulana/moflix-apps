package com.didik.moflix.data.mapper

import com.didik.moflix.data.response.MovieResponse
import com.didik.moflix.domain.model.MovieModel
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import io.mockk.unmockkAll

class MovieMapperTest : ShouldSpec({

    lateinit var movieMapper: MovieMapper

    beforeTest {
        movieMapper = MovieMapper()
    }

    afterTest {
        unmockkAll()
    }

    context("mapToDomain") {
        should("map movie response into movie model") {
            // Given
            val mockMovieResponse: MovieResponse = mockk()
            val mockMovieModel: MovieModel = mockk()

            every { movieMapper.mapToDomain(mockMovieResponse) } returns mockMovieModel

            // Then
            movieMapper.mapToDomain(mockMovieResponse) shouldBe mockMovieModel
        }
    }

})