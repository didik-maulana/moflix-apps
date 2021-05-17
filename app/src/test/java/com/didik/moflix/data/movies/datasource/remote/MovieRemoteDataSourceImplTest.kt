package com.didik.moflix.data.movies.datasource.remote

import android.content.Context
import com.didik.moflix.app.MoflixApp
import com.didik.moflix.data.movies.datasource.remote.response.MovieListResponse
import com.didik.moflix.data.movies.datasource.remote.response.MovieResponse
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.*

class MovieRemoteDataSourceImplTest : ShouldSpec({

    lateinit var movieRemoteDataSourceImpl: MovieRemoteDataSourceImpl

    beforeTest {
        mockkObject(MoflixApp)
        mockkObject(JSONHelper)

        movieRemoteDataSourceImpl = spyk(MovieRemoteDataSourceImpl())
    }

    afterTest {
        unmockkAll()
    }

    context("getMovies") {
        should("fetch from local json and return list of movie model") {
            // Given
            val mockContext: Context = mockk()
            val mockMovies: List<MovieResponse> = mockk()
            val mockMovieListResponse = spyk(MovieListResponse(mockMovies))

            every { MoflixApp.applicationContext } returns mockContext
            every { JSONHelper.readMoviesJson() } returns mockMovieListResponse

            // Then
            movieRemoteDataSourceImpl.getMovies() shouldBe mockMovies
            verify(exactly = 1) { JSONHelper.readMoviesJson() }
        }
    }

})