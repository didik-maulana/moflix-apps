package com.didik.moflix.data.repository

import com.didik.moflix.data.datasource.SeriesLocalDataSourceImpl
import com.didik.moflix.data.mapper.SeriesMapper
import com.didik.moflix.data.response.SeriesResponse
import com.didik.moflix.domain.model.MovieModel
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.*

class SeriesRepositoryImplTest : ShouldSpec({

    val mockLocalDataSource: SeriesLocalDataSourceImpl = mockk()
    val mockMapper: SeriesMapper = mockk()
    lateinit var seriesRepositoryImpl: SeriesRepositoryImpl

    beforeTest {
        seriesRepositoryImpl = spyk(
            SeriesRepositoryImpl(
                localDataSource = mockLocalDataSource,
                mapper = mockMapper
            )
        )
    }

    context("getSeries") {
        should("get series from local data source, mapping, and return list of movie") {
            // Given
            val mockSeriesResponseList: List<SeriesResponse> = mockk()
            val mockMovieModelList: List<MovieModel> = mockk()

            coEvery { mockLocalDataSource.getSeries() } returns mockSeriesResponseList
            every { mockMapper.mapToListDomain(mockSeriesResponseList) } returns mockMovieModelList

            // Then
            seriesRepositoryImpl.getSeries() shouldBe mockMovieModelList

            coVerify(exactly = 1) { mockLocalDataSource.getSeries() }
            verify(exactly = 1) { mockMapper.mapToListDomain(mockSeriesResponseList) }
        }
    }

})