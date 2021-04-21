package com.didik.moflix.domain.usecase

import com.didik.moflix.data.series.repository.SeriesRepositoryImpl
import com.didik.moflix.domain.model.MovieModel
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.spyk

class SeriesUseCaseTest : ShouldSpec({

    val mockRepository: SeriesRepositoryImpl = mockk()
    lateinit var seriesUseCase: SeriesUseCase

    beforeTest {
        seriesUseCase = spyk(SeriesUseCase(mockRepository))
    }

    context("getSeries") {
        should("get series from repository and return list of movie") {
            // Given
            val mockMovieModelList: List<MovieModel> = mockk()

            coEvery { mockRepository.getSeries() } returns mockMovieModelList

            // Then
            seriesUseCase.getSeries() shouldBe mockMovieModelList
        }
    }

})