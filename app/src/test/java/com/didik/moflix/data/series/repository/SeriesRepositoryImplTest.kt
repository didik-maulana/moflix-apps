package com.didik.moflix.data.series.repository

//class SeriesRepositoryImplTest : ShouldSpec({
//
//    val remoteDataSource: SeriesRemoteDataSourceImpl = mockk()
//    val seriesMapper: SeriesMapper = mockk()
//    lateinit var seriesRepositoryImpl: SeriesRepositoryImpl
//
//    beforeTest {
//        seriesRepositoryImpl = SeriesRepositoryImpl(
//            remoteDataSource = remoteDataSource,
//            mapper = seriesMapper
//        )
//    }
//
//    afterTest {
//        unmockkAll()
//    }
//
//    context("getSeries") {
//        should("return result state list of movie model when response is successful") {
//            // Given
//            val fakeResults: List<SeriesResponse> = mockk()
//            val fakeMovieModels: List<MovieModel> = mockk()
//            val fakeResponse: Response<SeriesListResponse> = mockk()
//
//            every { fakeResponse.isSuccessful } returns true
//            every { fakeResponse.body()?.results } returns fakeResults
//            every { seriesMapper.mapResponseToListDomain(fakeResults) } returns fakeMovieModels
//            coEvery { remoteDataSource.getSeries() } returns fakeResponse
//
//            // When
//            val resultState = seriesRepositoryImpl.getSeries()
//
//            // Then
//            resultState shouldBe ResultState.Success(fakeMovieModels)
//            verify(exactly = 1) { seriesMapper.mapResponseToListDomain(fakeResults) }
//            coVerify(exactly = 1) { remoteDataSource.getSeries() }
//        }
//
//        should("return result state failure when response is not successful") {
//            // Given
//            val fakeMessage = Faker.string
//            val fakeResponse: Response<SeriesListResponse> = mockk()
//
//            every { fakeResponse.isSuccessful } returns false
//            every { fakeResponse.message() } returns fakeMessage
//            coEvery { remoteDataSource.getSeries() } returns fakeResponse
//
//            // When
//            val resultState = seriesRepositoryImpl.getSeries()
//
//            // Then
//            resultState shouldBe ResultState.Failure(fakeMessage)
//        }
//    }
//
//    context("getSeriesDetail") {
//        should("return result state of movie model when response is successful") {
//            // Given
//            val fakeSeriesId = Faker.int
//            val fakeMovieModel: MovieModel = mockk()
//            val fakeSeriesResponse: SeriesResponse = mockk()
//            val fakeResponse: Response<SeriesResponse> = mockk()
//
//            every { fakeResponse.isSuccessful } returns true
//            every { fakeResponse.body() } returns fakeSeriesResponse
//            every { seriesMapper.mapResponseToDomain(fakeSeriesResponse) } returns fakeMovieModel
//            coEvery { remoteDataSource.getSeriesDetail(fakeSeriesId) } returns fakeResponse
//
//            // When
//            val resultState = seriesRepositoryImpl.getSeriesDetail(fakeSeriesId)
//
//            // Then
//            resultState shouldBe ResultState.Success(fakeMovieModel)
//            verify(exactly = 1) { seriesMapper.mapResponseToDomain(fakeSeriesResponse) }
//            coVerify(exactly = 1) { remoteDataSource.getSeriesDetail(fakeSeriesId) }
//        }
//
//        should("return result state failure when response is not successful") {
//            // Given
//            val fakeSeriesId = Faker.int
//            val fakeMessage = Faker.string
//            val fakeResponse: Response<SeriesResponse> = mockk()
//
//            every { fakeResponse.isSuccessful } returns false
//            every { fakeResponse.message() } returns fakeMessage
//            every { fakeResponse.body() } returns null
//            coEvery { remoteDataSource.getSeriesDetail(fakeSeriesId) } returns fakeResponse
//
//            // When
//            val resultState = seriesRepositoryImpl.getSeriesDetail(fakeSeriesId)
//
//            // Then
//            resultState shouldBe ResultState.Failure(fakeMessage)
//        }
//    }
//
//})