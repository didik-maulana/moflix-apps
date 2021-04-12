package com.didik.moflix.data.movies.datasource.remote

import com.didik.moflix.data.movies.datasource.remote.response.MovieResponse
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll

class MovieRemoteDataSourceTest : ShouldSpec({

    lateinit var movieRemoteDataSource: MovieRemoteDataSource

    beforeTest {
        movieRemoteDataSource = mockk()
    }

    afterTest {
        unmockkAll()
    }

    context("getMovies") {
        should("return list of movie response") {
            // Given
            val mockMovieResponseList: List<MovieResponse> = mockk()

            coEvery { movieRemoteDataSource.getMovies() } returns mockMovieResponseList

            // Then
            movieRemoteDataSource.getMovies() shouldBe mockMovieResponseList
        }
    }

})