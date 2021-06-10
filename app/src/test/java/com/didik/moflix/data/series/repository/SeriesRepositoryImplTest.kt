package com.didik.moflix.data.series.repository

import androidx.sqlite.db.SupportSQLiteQuery
import com.didik.moflix.data.series.datasource.local.SeriesLocalDataSource
import com.didik.moflix.data.series.datasource.local.entities.SeriesEntity
import com.didik.moflix.data.series.datasource.remote.SeriesRemoteDataSource
import com.didik.moflix.data.series.datasource.remote.response.SeriesListResponse
import com.didik.moflix.data.series.datasource.remote.response.SeriesResponse
import com.didik.moflix.data.series.mapper.SeriesMapper
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.domain.repository.SeriesRepository
import com.didik.moflix.helpers.Faker
import com.didik.moflix.utils.state.ResultState
import io.kotest.core.spec.style.ShouldSpec
import io.kotest.matchers.shouldBe
import io.mockk.*
import retrofit2.Response
import java.net.UnknownHostException

class SeriesRepositoryImplTest : ShouldSpec({

    val remoteDataSource: SeriesRemoteDataSource = mockk()
    val localDataSource: SeriesLocalDataSource = mockk()
    val seriesMapper: SeriesMapper = mockk()
    lateinit var seriesRepository: SeriesRepository

    val fakeSeriesId = Faker.int

    beforeTest {
        seriesRepository = SeriesRepositoryImpl(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
            mapper = seriesMapper
        )
    }

    afterTest {
        unmockkAll()
    }

    context("get series") {
        val fakeMessage = Faker.string
        val fakeResponse: Response<SeriesListResponse> = mockk()

        should("return result state list of movie model when response is successful") {
            // Given
            val fakeResults: List<SeriesResponse> = mockk()
            val fakeMovieModels: List<MovieModel> = mockk()

            every { fakeResponse.isSuccessful } returns true
            every { fakeResponse.body()?.results } returns fakeResults
            every { seriesMapper.mapResponseToListDomain(fakeResults) } returns fakeMovieModels
            coEvery { remoteDataSource.getSeries() } returns fakeResponse

            // When
            val resultState = seriesRepository.getSeries()

            // Then
            resultState shouldBe ResultState.Success(fakeMovieModels)
            verify(exactly = 1) { seriesMapper.mapResponseToListDomain(fakeResults) }
            coVerify(exactly = 1) { remoteDataSource.getSeries() }
        }

        should("return result state failure when response is not successful") {
            // Given
            every { fakeResponse.isSuccessful } returns false
            every { fakeResponse.message() } returns fakeMessage
            coEvery { remoteDataSource.getSeries() } returns fakeResponse

            // When
            val resultState = seriesRepository.getSeries()

            // Then
            resultState shouldBe ResultState.Failure(fakeMessage)
        }

        should("return result state failure when catch exception") {
            // Given
            coEvery { remoteDataSource.getSeries() } throws UnknownHostException(fakeMessage)

            // When
            val resultState = seriesRepository.getSeries()

            // Then
            resultState shouldBe ResultState.Failure(fakeMessage)
        }
    }

    context("get series detail") {
        val fakeMessage = Faker.string
        val fakeResponse: Response<SeriesResponse> = mockk()

        should("return result state of movie model when response is successful") {
            // Given
            val fakeMovieModel: MovieModel = mockk()
            val fakeSeriesResponse: SeriesResponse = mockk()

            every { fakeResponse.isSuccessful } returns true
            every { fakeResponse.body() } returns fakeSeriesResponse
            every { seriesMapper.mapResponseToDomain(fakeSeriesResponse) } returns fakeMovieModel
            coEvery { remoteDataSource.getSeriesDetail(fakeSeriesId) } returns fakeResponse

            // When
            val resultState = seriesRepository.getSeriesDetail(fakeSeriesId)

            // Then
            resultState shouldBe ResultState.Success(fakeMovieModel)
            verify(exactly = 1) { seriesMapper.mapResponseToDomain(fakeSeriesResponse) }
            coVerify(exactly = 1) { remoteDataSource.getSeriesDetail(fakeSeriesId) }
        }

        should("return result state failure when response is not successful") {
            // Given
            every { fakeResponse.isSuccessful } returns false
            every { fakeResponse.message() } returns fakeMessage
            every { fakeResponse.body() } returns null
            coEvery { remoteDataSource.getSeriesDetail(fakeSeriesId) } returns fakeResponse

            // When
            val resultState = seriesRepository.getSeriesDetail(fakeSeriesId)

            // Then
            resultState shouldBe ResultState.Failure(fakeMessage)
        }

        should("return result state failure when catch exception") {
            // Given
            coEvery { remoteDataSource.getSeriesDetail(fakeSeriesId) } throws UnknownHostException(
                fakeMessage
            )

            // When
            val resultState = seriesRepository.getSeriesDetail(fakeSeriesId)

            // Then
            resultState shouldBe ResultState.Failure(fakeMessage)
        }
    }

    context("get favorite series") {
        should("call get series from local data source and call mapper") {
            // Given
            val fakeQuery: SupportSQLiteQuery = mockk()

            every { seriesMapper.getMapperEntityToDomain() } returns mockk()
            every { localDataSource.getSeries(fakeQuery) } returns spyk()

            // When
            seriesRepository.getFavoriteSeries(fakeQuery)

            // Then
            verify(exactly = 1) { localDataSource.getSeries(fakeQuery) }
            verify(exactly = 1) { seriesMapper.getMapperEntityToDomain() }
        }
    }

    context("check favorite series") {
        should("return true when favorite series more than 0") {
            // Given
            every { localDataSource.getCountSeriesById(fakeSeriesId) } returns 1

            // When
            val isFavorite = seriesRepository.checkFavoriteSeries(fakeSeriesId)

            // Then
            isFavorite shouldBe true
            verify { localDataSource.getCountSeriesById(fakeSeriesId) }
        }

        should("return false when favorite movies is 0") {
            // Given
            every { localDataSource.getCountSeriesById(fakeSeriesId) } returns 0

            // When
            val isFavorite = seriesRepository.checkFavoriteSeries(fakeSeriesId)

            // Then
            isFavorite shouldBe false
            verify { localDataSource.getCountSeriesById(fakeSeriesId) }
        }
    }

    context("insert favorite series") {
        should("map domain to entity and insert from local data source") {
            // Given
            val fakeSeries: MovieModel = mockk()
            val fakeSeriesEntity: SeriesEntity = mockk()

            every { seriesMapper.mapDomainToEntity(fakeSeries) } returns fakeSeriesEntity
            coEvery { localDataSource.insertSeries(fakeSeriesEntity) } just runs

            // When
            seriesRepository.insertFavoriteSeries(fakeSeries)

            // Then
            verify(exactly = 1) { seriesMapper.mapDomainToEntity(fakeSeries) }
            verify(exactly = 1) { localDataSource.insertSeries(fakeSeriesEntity) }
        }
    }

    context("delete favorite series") {
        should("map domain to entity and delete from local data source") {
            // Given
            val fakeSeries: MovieModel = mockk()
            val fakeSeriesEntity: SeriesEntity = mockk()

            every { seriesMapper.mapDomainToEntity(fakeSeries) } returns fakeSeriesEntity
            coEvery { localDataSource.deleteSeries(fakeSeriesEntity) } just runs

            // When
            seriesRepository.deleteFavoriteSeries(fakeSeries)

            // Then
            verify(exactly = 1) { seriesMapper.mapDomainToEntity(fakeSeries) }
            verify(exactly = 1) { localDataSource.deleteSeries(fakeSeriesEntity) }
        }
    }

})