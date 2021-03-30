package com.didik.moflix.data.datasource

import com.didik.moflix.data.response.MovieResponse
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll

class MovieLocalDataSourceTest : ShouldSpec({

    lateinit var movieLocalDataSource: MovieLocalDataSource

    beforeTest {
        movieLocalDataSource = mockk()
    }

    afterTest {
        unmockkAll()
    }

    context("getMovies") {
        should("return list of movie response") {
            // Given
            val mockMovieResponseList: List<MovieResponse> = mockk()

            coEvery { movieLocalDataSource.getMovies() } returns mockMovieResponseList

            // Then
            movieLocalDataSource.getMovies() shouldBe mockMovieResponseList
        }
    }

})