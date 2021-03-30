package com.didik.moflix.domain.usecase

import com.didik.moflix.data.repository.SeriesRepositoryImpl
import com.didik.moflix.domain.model.MovieModel
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk

class GetSeriesUseCaseTest : ShouldSpec({

    val mockRepository: SeriesRepositoryImpl = mockk()
    lateinit var getSeriesUseCase: GetSeriesUseCase

    beforeTest {
        getSeriesUseCase = spyk(GetSeriesUseCase(mockRepository))
    }

    context("getSeries") {
        should("get series from repository and return list of movie") {
            // Given
            val mockMovieModelList: List<MovieModel> = mockk()

            coEvery { mockRepository.getSeries() } returns mockMovieModelList

            // Then
            getSeriesUseCase.getSeries() shouldBe mockMovieModelList
        }
    }

})