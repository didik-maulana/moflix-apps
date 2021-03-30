package com.didik.moflix.domain.usecase

import com.didik.moflix.data.repository.MovieRepositoryImpl
import com.didik.moflix.domain.model.MovieModel
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.unmockkAll

class GetMoviesUseCaseTest : ShouldSpec({

    val mockRepository: MovieRepositoryImpl = mockk()
    lateinit var getMoviesUseCase: GetMoviesUseCase

    beforeTest {
        getMoviesUseCase = spyk(GetMoviesUseCase(mockRepository))
    }

    afterTest {
        unmockkAll()
    }

    context("getMovies") {
        should("get movies from repository and return list of movie") {
            // Given
            val mockMovieModelList: List<MovieModel> = mockk()

            coEvery { mockRepository.getMovies() } returns mockMovieModelList

            // Then
            getMoviesUseCase.getMovies() shouldBe mockMovieModelList
        }
    }

})