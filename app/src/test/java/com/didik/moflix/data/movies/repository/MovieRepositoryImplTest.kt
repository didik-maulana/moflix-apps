package com.didik.moflix.data.movies.repository

import androidx.sqlite.db.SupportSQLiteQuery
import com.didik.moflix.data.movies.datasource.local.MovieLocalDataSource
import com.didik.moflix.data.movies.datasource.local.entities.MovieEntity
import com.didik.moflix.data.movies.datasource.remote.MovieRemoteDataSource
import com.didik.moflix.data.movies.datasource.remote.response.MovieListResponse
import com.didik.moflix.data.movies.datasource.remote.response.MovieResponse
import com.didik.moflix.data.movies.mapper.MovieMapper
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.domain.repository.MovieRepository
import com.didik.moflix.helpers.Faker
import com.didik.moflix.utils.state.ResultState
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import retrofit2.Response
import java.net.UnknownHostException

class MovieRepositoryImplTest : ShouldSpec({

    val remoteDataSource: MovieRemoteDataSource = mockk()
    val localDataSource: MovieLocalDataSource = mockk()
    val movieMapper: MovieMapper = mockk()
    lateinit var movieRepository: MovieRepository

    val fakeMovieId = Faker.int

    beforeTest {
        movieRepository = MovieRepositoryImpl(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
            mapper = movieMapper
        )
    }

    afterTest {
        unmockkAll()
    }

    context("get movies") {
        val fakeMessage = Faker.string
        val fakeResponse: Response<MovieListResponse> = mockk()

        should("return result state list of movie model when response is successful") {
            // Given
            val fakeResults: List<MovieResponse> = mockk()
            val fakeMovieModels: List<MovieModel> = mockk()

            every { fakeResponse.isSuccessful } returns true
            every { fakeResponse.body()?.results } returns fakeResults
            every { movieMapper.mapResponseToListDomain(fakeResults) } returns fakeMovieModels
            coEvery { remoteDataSource.getMovies() } returns fakeResponse

            // When
            val resultState = movieRepository.getMovies()

            // Then
            resultState shouldBe ResultState.Success(fakeMovieModels)
            coVerify(exactly = 1) { remoteDataSource.getMovies() }
            verify(exactly = 1) { movieMapper.mapResponseToListDomain(fakeResults) }
        }

        should("return result state failure when response is failure") {
            // Given
            every { fakeResponse.isSuccessful } returns false
            every { fakeResponse.message() } returns fakeMessage
            coEvery { remoteDataSource.getMovies() } returns fakeResponse

            // When
            val resultState = movieRepository.getMovies()

            // Then
            resultState shouldBe ResultState.Failure(fakeMessage)
        }

        should("return result state failure when catch exception") {
            // Given
            coEvery { remoteDataSource.getMovies() } throws UnknownHostException(fakeMessage)

            // When
            val resultState = movieRepository.getMovies()

            // Then
            resultState shouldBe ResultState.Failure(fakeMessage)
        }
    }

    context("get movie detail") {
        val fakeMessage = Faker.string
        val fakeResponse: Response<MovieResponse> = mockk()

        should("return result state movie model when response is successful") {
            // Given
            val fakeMovieResponse: MovieResponse = mockk()
            val fakeMovieModel: MovieModel = mockk()

            every { fakeResponse.isSuccessful } returns true
            every { fakeResponse.body() } returns fakeMovieResponse
            every { movieMapper.mapResponseToDomain(fakeMovieResponse) } returns fakeMovieModel
            coEvery { remoteDataSource.getMovieDetail(fakeMovieId) } returns fakeResponse

            // When
            val resultState = movieRepository.getMovieDetail(fakeMovieId)

            // Then
            resultState shouldBe ResultState.Success(fakeMovieModel)
            verify(exactly = 1) { movieMapper.mapResponseToDomain(fakeMovieResponse) }
            coVerify(exactly = 1) { remoteDataSource.getMovieDetail(fakeMovieId) }
        }

        should("return result state failure when response is failure") {
            // Given
            every { fakeResponse.isSuccessful } returns false
            every { fakeResponse.body() } returns null
            every { fakeResponse.message() } returns fakeMessage
            coEvery { remoteDataSource.getMovieDetail(fakeMovieId) } returns fakeResponse

            // When
            val resultState = movieRepository.getMovieDetail(fakeMovieId)

            // Then
            resultState shouldBe ResultState.Failure(fakeMessage)
        }

        should("return result state failure when catch exception") {
            // Given
            coEvery {
                remoteDataSource.getMovieDetail(fakeMovieId)
            } throws UnknownHostException(fakeMessage)

            // When
            val resultState = movieRepository.getMovieDetail(fakeMovieId)

            // Then
            resultState shouldBe ResultState.Failure(fakeMessage)
        }
    }

    context("get favorite movies") {
        should("call get movies from local data source and call mapper") {
            // Given
            val fakeQuery: SupportSQLiteQuery = mockk()

            every { movieMapper.getMapperEntityToDomain() } returns mockk()
            every { localDataSource.getMovies(fakeQuery) } returns spyk()

            // When
            movieRepository.getFavoriteMovies(fakeQuery)

            // Then
            verify(exactly = 1) { localDataSource.getMovies(fakeQuery) }
            verify { movieMapper.getMapperEntityToDomain() }
        }
    }

    context("check favorite movie") {
        should("return true when favorite movies more than 0") {
            // Given
            every { localDataSource.getCountMovieById(fakeMovieId) } returns 1

            // When
            val isFavorite = movieRepository.checkFavoriteMovie(fakeMovieId)

            // Then
            isFavorite shouldBe true
            verify { localDataSource.getCountMovieById(fakeMovieId) }
        }

        should("return false when favorite movies is 0") {
            // Given
            every { localDataSource.getCountMovieById(fakeMovieId) } returns 0

            // When
            val isFavorite = movieRepository.checkFavoriteMovie(fakeMovieId)

            // Then
            isFavorite shouldBe false
            verify { localDataSource.getCountMovieById(fakeMovieId) }
        }
    }

    context("insert favorite movie") {
        should("map domain to entity and insert from local data source") {
            // Given
            val fakeMovieModel: MovieModel = mockk()
            val fakeMovieEntity: MovieEntity = mockk()

            every { movieMapper.mapDomainToEntity(fakeMovieModel) } returns fakeMovieEntity
            coEvery { localDataSource.insertMovie(fakeMovieEntity) } just runs

            // When
            movieRepository.insertFavoriteMovie(fakeMovieModel)

            // Then
            verify(exactly = 1) { movieMapper.mapDomainToEntity(fakeMovieModel) }
            verify(exactly = 1) { localDataSource.insertMovie(fakeMovieEntity) }
        }
    }

    context("delete favorite movie") {
        should("map domain to entity and delete from local data source") {
            // Given
            val fakeMovieModel: MovieModel = mockk()
            val fakeMovieEntity: MovieEntity = mockk()

            every { movieMapper.mapDomainToEntity(fakeMovieModel) } returns fakeMovieEntity
            coEvery { localDataSource.deleteMovie(fakeMovieEntity) } just runs

            // When
            movieRepository.deleteFavoriteMovie(fakeMovieModel)

            // Then
            verify(exactly = 1) { movieMapper.mapDomainToEntity(fakeMovieModel) }
            verify(exactly = 1) { localDataSource.deleteMovie(fakeMovieEntity) }
        }
    }

})