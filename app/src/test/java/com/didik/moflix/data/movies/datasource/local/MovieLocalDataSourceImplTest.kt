package com.didik.moflix.data.movies.datasource.local

import androidx.paging.DataSource
import androidx.sqlite.db.SupportSQLiteQuery
import com.didik.moflix.data.movies.datasource.local.entities.MovieEntity
import com.didik.moflix.data.movies.datasource.local.room.MovieDao
import com.didik.moflix.helpers.Faker
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.*

class MovieLocalDataSourceImplTest : ShouldSpec({

    val movieDao: MovieDao = mockk()
    lateinit var localDataSource: MovieLocalDataSource

    beforeTest {
        localDataSource = MovieLocalDataSourceImpl(movieDao)
    }

    afterTest {
        unmockkAll()
    }

    context("getMovies") {
        should("call get movies from movie dao and return data source factory") {
            // Given
            val fakeQuery: SupportSQLiteQuery = mockk()
            val fakeFavoriteMovies: DataSource.Factory<Int, MovieEntity> = mockk()

            every { movieDao.getMovies(fakeQuery) } returns fakeFavoriteMovies

            // When
            val result = localDataSource.getMovies(fakeQuery)

            // Then
            result shouldBe fakeFavoriteMovies
            verify(exactly = 1) { movieDao.getMovies(fakeQuery) }
        }
    }

    context("getCountMovieById") {
        should("call get count movie by id from movie dao and return integer") {
            // Given
            val fakeCount = Faker.int
            val fakeMovieId = Faker.int

            every { movieDao.getCountMovieById(fakeMovieId) } returns fakeCount

            // When
            val result = localDataSource.getCountMovieById(fakeMovieId)

            // Then
            result shouldBe fakeCount
            verify(exactly = 1) { movieDao.getCountMovieById(fakeMovieId) }
        }
    }

    context("insertMovie") {
        should("call insert movie from movie dao") {
            // Given
            val fakeMovie: MovieEntity = mockk()

            every { movieDao.insertMovie(fakeMovie) } just runs

            // When
            localDataSource.insertMovie(fakeMovie)

            // Then
            verify(exactly = 1) { movieDao.insertMovie(fakeMovie) }
        }
    }

    context("deleteMovie") {
        should("call delete movie from movie dao") {
            // Given
            val fakeMovie: MovieEntity = mockk()

            every { movieDao.deleteMovie(fakeMovie) } just runs

            // When
            localDataSource.deleteMovie(fakeMovie)

            // Then
            verify(exactly = 1) { movieDao.deleteMovie(fakeMovie) }
        }
    }

})