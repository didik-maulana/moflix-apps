package com.didik.moflix.data.movies.datasource.remote

import com.didik.moflix.data.movies.datasource.remote.response.MovieListResponse
import com.didik.moflix.data.movies.datasource.remote.response.MovieResponse
import com.didik.moflix.helpers.Faker
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import retrofit2.Response

class MovieRemoteDataSourceTest : ShouldSpec({

    lateinit var movieRemoteDataSource: MovieRemoteDataSource

    beforeTest {
        movieRemoteDataSource = mockk()
    }

    afterTest {
        unmockkAll()
    }

    context("getMovies") {
        should("return response of movie list response") {
            // Given
            val mockMoviesResponse: Response<MovieListResponse> = mockk()

            coEvery { movieRemoteDataSource.getMovies() } returns mockMoviesResponse

            // Then
            movieRemoteDataSource.getMovies() shouldBe mockMoviesResponse
        }
    }

    context("getMovieDetail") {
        should("return response of movie response") {
            // Given
            val fakeMovieId = Faker.int
            val mockMovieResponse: Response<MovieResponse> = mockk()

            coEvery { movieRemoteDataSource.getMovieDetail(fakeMovieId) } returns mockMovieResponse

            // Then
            movieRemoteDataSource.getMovieDetail(fakeMovieId) shouldBe mockMovieResponse
        }
    }

})