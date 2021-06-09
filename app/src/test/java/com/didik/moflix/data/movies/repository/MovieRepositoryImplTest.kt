package com.didik.moflix.data.movies.repository

import com.didik.moflix.data.movies.datasource.remote.MovieRemoteDataSourceImpl
import com.didik.moflix.data.movies.datasource.remote.response.MovieListResponse
import com.didik.moflix.data.movies.datasource.remote.response.MovieResponse
import com.didik.moflix.data.movies.mapper.MovieMapper
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.helpers.Faker
import com.didik.moflix.utils.state.ResultState
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import retrofit2.Response

class MovieRepositoryImplTest : ShouldSpec({

    val remoteDataSource: MovieRemoteDataSourceImpl = mockk()
    val movieMapper: MovieMapper = mockk()
    lateinit var movieRepositoryImpl: MovieRepositoryImpl

    beforeTest {
        movieRepositoryImpl = MovieRepositoryImpl(
            remoteDataSource = remoteDataSource,
            mapper = movieMapper
        )
    }

    afterTest {
        unmockkAll()
    }

    context("getMovies") {
        should("return result state list of movie model when response is successful") {
            // Given
            val fakeResults: List<MovieResponse> = mockk()
            val fakeResponse: Response<MovieListResponse> = mockk()
            val fakeMovieModels: List<MovieModel> = mockk()

            every { fakeResponse.isSuccessful } returns true
            every { fakeResponse.body()?.results } returns fakeResults
            every { movieMapper.mapResponseToListDomain(fakeResults) } returns fakeMovieModels
            coEvery { remoteDataSource.getMovies() } returns fakeResponse

            // When
            val resultState = movieRepositoryImpl.getMovies()

            // Then
            resultState shouldBe ResultState.Success(fakeMovieModels)
            coVerify(exactly = 1) { remoteDataSource.getMovies() }
            verify(exactly = 1) { movieMapper.mapResponseToListDomain(fakeResults) }
        }

        should("return result state failure when response is failure") {
            // Given
            val fakeMessage = Faker.string
            val fakeResponse: Response<MovieListResponse> = mockk()

            every { fakeResponse.isSuccessful } returns false
            every { fakeResponse.message() } returns fakeMessage
            coEvery { remoteDataSource.getMovies() } returns fakeResponse

            // When
            val resultState = movieRepositoryImpl.getMovies()

            // Then
            resultState shouldBe ResultState.Failure(fakeMessage)
        }
    }

    context("getMovieDetail") {
        should("return result state movie model when response is successful") {
            // Given
            val fakeMovieId = Faker.int
            val fakeResponse: Response<MovieResponse> = mockk()
            val fakeMovieResponse: MovieResponse = mockk()
            val fakeMovieModel: MovieModel = mockk()

            every { fakeResponse.isSuccessful } returns true
            every { fakeResponse.body() } returns fakeMovieResponse
            every { movieMapper.mapResponseToDomain(fakeMovieResponse) } returns fakeMovieModel
            coEvery { remoteDataSource.getMovieDetail(fakeMovieId) } returns fakeResponse

            // When
            val resultState = movieRepositoryImpl.getMovieDetail(fakeMovieId)

            // Then
            resultState shouldBe ResultState.Success(fakeMovieModel)
            verify(exactly = 1) { movieMapper.mapResponseToDomain(fakeMovieResponse) }
            coVerify(exactly = 1) { remoteDataSource.getMovieDetail(fakeMovieId) }
        }

        should("return result state failure when response is failure") {
            // Given
            val fakeMovieId = Faker.int
            val fakeMessage = Faker.string
            val fakeResponse: Response<MovieResponse> = mockk()

            every { fakeResponse.isSuccessful } returns false
            every { fakeResponse.body() } returns null
            every { fakeResponse.message() } returns fakeMessage
            coEvery { remoteDataSource.getMovieDetail(fakeMovieId) } returns fakeResponse

            // When
            val resultState = movieRepositoryImpl.getMovieDetail(fakeMovieId)

            // Then
            resultState shouldBe ResultState.Failure(fakeMessage)
        }
    }

})