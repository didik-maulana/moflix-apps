package com.didik.moflix.di

import com.didik.moflix.data.routes.ApiServices
import com.didik.moflix.data.series.datasource.remote.SeriesRemoteDataSource
import com.didik.moflix.data.series.datasource.remote.SeriesRemoteDataSourceImpl
import com.didik.moflix.data.series.mapper.SeriesMapper
import com.didik.moflix.data.series.repository.SeriesRepositoryImpl
import com.didik.moflix.domain.repository.SeriesRepository
import com.didik.moflix.domain.usecase.SeriesUseCase
import com.didik.moflix.presentation.series.SeriesViewModel
import com.didik.moflix.utils.dispatcher.AppDispatchers
import com.didik.moflix.utils.dispatcher.DispatchersProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class SeriesModule {

    @Provides
    fun provideSeriesLocalDataSource(apiServices: ApiServices): SeriesRemoteDataSource {
        return SeriesRemoteDataSourceImpl(apiServices)
    }

    @Provides
    fun provideSeriesMapper(): SeriesMapper = SeriesMapper()

    @Provides
    fun provideSeriesRepository(
        remoteDataSource: SeriesRemoteDataSource,
        mapper: SeriesMapper
    ): SeriesRepository = SeriesRepositoryImpl(remoteDataSource, mapper)

    @Provides
    fun provideSeriesUseCase(
        repository: SeriesRepository,
        dispatchers: DispatchersProvider
    ) = SeriesUseCase(repository, dispatchers)
}