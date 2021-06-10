package com.didik.moflix.data.movies.datasource.remote

import com.didik.moflix.data.movies.datasource.remote.response.MovieListResponse
import com.didik.moflix.data.movies.datasource.remote.response.MovieResponse
import com.didik.moflix.data.routes.ApiServices
import com.didik.moflix.helpers.Faker
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import retrofit2.Response

class MovieRemoteDataSourceImplTest : ShouldSpec({

    val apiServices: ApiServices = mockk()
    lateinit var movieRemoteDataSourceImpl: MovieRemoteDataSourceImpl

    beforeTest {
        movieRemoteDataSourceImpl = MovieRemoteDataSourceImpl(apiServices)
    }

    afterTest {
        unmockkAll()
    }

    context("get movies") {
        should("fetch from api services and return response of movie list response") {
            // Given
            val mockMoviesResponse: Response<MovieListResponse> = mockk()

            coEvery { apiServices.getPopularMovies() } returns mockMoviesResponse

            // When
            val result = movieRemoteDataSourceImpl.getMovies()

            // Then
            result shouldBe mockMoviesResponse
            coVerify(exactly = 1) { apiServices.getPopularMovies() }
        }
    }

    context("get movie detail") {
        should("fetch from api services and return response of movie response") {
            // Given
            val fakeMovieId = Faker.int
            val mockMovieResponse: Response<MovieResponse> = mockk()

            coEvery { apiServices.getMovieDetail(fakeMovieId) } returns mockMovieResponse

            // When
            val result = movieRemoteDataSourceImpl.getMovieDetail(fakeMovieId)

            // Then
            result shouldBe mockMovieResponse
            coVerify(exactly = 1) { apiServices.getMovieDetail(fakeMovieId) }
        }
    }

})