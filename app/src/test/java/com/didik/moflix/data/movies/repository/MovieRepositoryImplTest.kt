package com.didik.moflix.data.movies.repository

import com.didik.moflix.data.movies.datasource.remote.MovieRemoteDataSourceImpl
import com.didik.moflix.data.movies.datasource.remote.response.MovieResponse
import com.didik.moflix.data.movies.mapper.MovieMapper
import com.didik.moflix.domain.model.MovieModel
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.*

class MovieRepositoryImplTest : ShouldSpec({

    val mockLocalDataSource: MovieRemoteDataSourceImpl = mockk()
    val mockMapper: MovieMapper = mockk()
    lateinit var movieRepositoryImpl: MovieRepositoryImpl

    beforeTest {
        movieRepositoryImpl = spyk(
            MovieRepositoryImpl(
                localDataSource = mockLocalDataSource,
                mapper = mockMapper
            )
        )
    }

    afterTest {
        unmockkAll()
    }

    context("getMovies") {
        should("get movies from local data source, mapping and return list of movie") {
            // Given
            val mockMovieResponseList: List<MovieResponse> = mockk()
            val mockMovieModelList: List<MovieModel> = mockk()

            coEvery { mockLocalDataSource.getMovies() } returns mockMovieResponseList
            every { mockMapper.mapToListDomain(mockMovieResponseList) } returns mockMovieModelList

            // Then
            movieRepositoryImpl.getMovies() shouldBe mockMovieModelList

            coVerify(exactly = 1) { mockLocalDataSource.getMovies() }
            verify(exactly = 1) { mockMapper.mapToListDomain(mockMovieResponseList) }
        }
    }

})