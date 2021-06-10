package com.didik.moflix.domain.usecase

import androidx.paging.DataSource
import androidx.sqlite.db.SupportSQLiteQuery
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.domain.repository.MovieRepository
import com.didik.moflix.helpers.Faker
import com.didik.moflix.utils.dispatcher.DispatchersProvider
import com.didik.moflix.utils.dispatcher.TestDispatchers
import com.didik.moflix.utils.state.ResultState
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.*

class MoviesUseCaseTest : ShouldSpec({

    val repository: MovieRepository = mockk()
    val dispatchers: DispatchersProvider = TestDispatchers()
    lateinit var movieUseCase: MovieUseCase

    val fakeMovieId = Faker.int
    val fakeMovie: MovieModel = mockk()

    beforeTest {
        movieUseCase = MovieUseCase(repository, dispatchers)
    }

    afterTest {
        unmockkAll()
    }

    context("get movies") {
        should("get movies from repository and return result state of list movie model") {
            // Given
            val fakeResult: ResultState<List<MovieModel>> = mockk()

            coEvery { repository.getMovies() } returns fakeResult

            // When
            val result = movieUseCase.getMovies()

            // Then
            result shouldBe fakeResult
            coVerify(exactly = 1) { repository.getMovies() }
        }
    }

    context("get movie detail") {
        should("get movie detail from repository and return result state of movie model") {
            // Given
            val fakeResult: ResultState<MovieModel> = mockk()

            coEvery { repository.getMovieDetail(fakeMovieId) } returns fakeResult

            // When
            val result = movieUseCase.getMovieDetail(fakeMovieId)

            // Then
            result shouldBe fakeResult
            coVerify(exactly = 1) { repository.getMovies() }
        }
    }

    context("get favorite movies") {
        should("get favorite movies from repository and return data source factory") {
            // Given
            val fakeQuery: SupportSQLiteQuery = mockk()
            val fakeFavoriteDataFactory: DataSource.Factory<Int, MovieModel> = mockk()

            coEvery { repository.getFavoriteMovies(fakeQuery) } returns fakeFavoriteDataFactory

            // When
            val result = movieUseCase.getFavoriteMovies(fakeQuery)

            result shouldBe fakeFavoriteDataFactory
            coVerify(exactly = 1) { repository.getFavoriteMovies(fakeQuery) }
        }
    }

    context("check favorite movie") {
        should("call check favorite movie from repository and return Boolean") {
            // Given
            val fakeIsFavorite = Faker.boolean

            coEvery { repository.checkFavoriteMovie(fakeMovieId) } returns fakeIsFavorite

            // When
            val isFavorite = movieUseCase.checkFavoriteMovie(fakeMovieId)

            // Then
            isFavorite shouldBe fakeIsFavorite
            coVerify(exactly = 1) { repository.checkFavoriteMovie(fakeMovieId) }
        }
    }

    context("insert favorite movie") {
        should("call insert favorite movie from repository") {
            // Given
            coEvery { repository.insertFavoriteMovie(fakeMovie) } just runs

            // When
            movieUseCase.insertFavoriteMovie(fakeMovie)

            // Then
            coVerify(exactly = 1) { repository.insertFavoriteMovie(fakeMovie) }
        }
    }

    context("delete favorite movie") {
        should("call delete favorite movie from repository") {
            // Given
            coEvery { repository.deleteFavoriteMovie(fakeMovie) } just runs

            // When
            movieUseCase.deleteFavoriteMovie(fakeMovie)

            // Then
            coVerify(exactly = 1) { repository.deleteFavoriteMovie(fakeMovie) }
        }
    }

})