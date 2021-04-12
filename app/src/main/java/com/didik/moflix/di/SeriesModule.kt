package com.didik.moflix.di

import com.didik.moflix.data.series.datasource.remote.SeriesRemoteDataSource
import com.didik.moflix.data.series.datasource.remote.SeriesRemoteDataSourceImpl
import com.didik.moflix.data.series.mapper.SeriesMapper
import com.didik.moflix.data.series.repository.SeriesRepositoryImpl
import com.didik.moflix.domain.repository.SeriesRepository
import com.didik.moflix.domain.usecase.GetSeriesUseCase
import com.didik.moflix.presentation.series.SeriesViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class SeriesModule {

    @Provides
    fun provideSeriesLocalDataSource(): SeriesRemoteDataSource = SeriesRemoteDataSourceImpl()

    @Provides
    fun provideSeriesMapper(): SeriesMapper = SeriesMapper()

    @Provides
    fun provideSeriesRepository(
        remoteDataSource: SeriesRemoteDataSource,
        mapper: SeriesMapper
    ): SeriesRepository = SeriesRepositoryImpl(remoteDataSource, mapper)

    @Provides
    fun provideGetSeriesUseCase(repository: SeriesRepository) = GetSeriesUseCase(repository)

    @Provides
    fun provideSeriesViewModel(getSeriesUseCase: GetSeriesUseCase) = SeriesViewModel(getSeriesUseCase)
}