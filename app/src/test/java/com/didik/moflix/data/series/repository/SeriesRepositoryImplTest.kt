package com.didik.moflix.data.series.repository

import com.didik.moflix.data.series.datasource.remote.SeriesRemoteDataSourceImpl
import com.didik.moflix.data.series.datasource.remote.response.SeriesResponse
import com.didik.moflix.data.series.mapper.SeriesMapper
import com.didik.moflix.domain.model.MovieModel
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.*

class SeriesRepositoryImplTest : ShouldSpec({

    val mockRemoteDataSource: SeriesRemoteDataSourceImpl = mockk()
    val mockMapper: SeriesMapper = mockk()
    lateinit var seriesRepositoryImpl: SeriesRepositoryImpl

    beforeTest {
        seriesRepositoryImpl = spyk(
            SeriesRepositoryImpl(
                localDataSource = mockRemoteDataSource,
                mapper = mockMapper
            )
        )
    }

    context("getSeries") {
        should("get series from local data source, mapping, and return list of movie") {
            // Given
            val mockSeriesResponseList: List<SeriesResponse> = mockk()
            val mockMovieModelList: List<MovieModel> = mockk()

            coEvery { mockRemoteDataSource.getSeries() } returns mockSeriesResponseList
            every { mockMapper.mapToListDomain(mockSeriesResponseList) } returns mockMovieModelList

            // Then
            seriesRepositoryImpl.getSeries() shouldBe mockMovieModelList

            coVerify(exactly = 1) { mockRemoteDataSource.getSeries() }
            verify(exactly = 1) { mockMapper.mapToListDomain(mockSeriesResponseList) }
        }
    }

})