package com.didik.moflix.data.routes

import com.didik.moflix.data.movies.datasource.remote.response.MovieListResponse
import com.didik.moflix.data.movies.datasource.remote.response.MovieResponse
import com.didik.moflix.data.series.datasource.remote.response.SeriesListResponse
import com.didik.moflix.data.series.datasource.remote.response.SeriesResponse
import com.didik.moflix.helpers.Faker
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.unmockkAll
import retrofit2.Response

class ApiServicesTest : ShouldSpec({

    lateinit var apiServices: ApiServices

    beforeTest {
        apiServices = mockk()
    }

    afterTest {
        unmockkAll()
    }

    context("getPopularMovies") {
        should("return response of movie list response") {
            // Given
            val fakeResponse: Response<MovieListResponse> = mockk()

            coEvery { apiServices.getPopularMovies() } returns fakeResponse

            // When
            val response = apiServices.getPopularMovies()

            // Then
            response shouldBe fakeResponse
        }
    }

    context("getPopularMovieDetail") {
        should("return response of movie response") {
            // Given
            val fakeMovieId = Faker.int
            val fakeResponse: Response<MovieResponse> = mockk()

            coEvery { apiServices.getMovieDetail(fakeMovieId) } returns fakeResponse

            // When
            val response = apiServices.getMovieDetail(fakeMovieId)

            // Then
            response shouldBe fakeResponse
        }
    }

    context("getPopularSeries") {
        should("return response of series list response") {
            // Given
            val fakeResponse: Response<SeriesListResponse> = mockk()

            coEvery { apiServices.getPopularSeries() } returns fakeResponse

            // When
            val response = apiServices.getPopularSeries()

            // Then
            response shouldBe fakeResponse
        }
    }

    context("getPopularSeriesDetail") {
        should("return response of series response") {
            // Given
            val fakeSeriesId = Faker.int
            val fakeResponse: Response<SeriesResponse> = mockk()

            coEvery { apiServices.getSeriesDetail(fakeSeriesId) } returns fakeResponse

            // When
            val response = apiServices.getSeriesDetail(fakeSeriesId)

            // Then
            response shouldBe fakeResponse
        }
    }

})