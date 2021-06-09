package com.didik.moflix.di

import com.didik.moflix.data.database.AppDatabase
import com.didik.moflix.data.routes.ApiServices
import com.didik.moflix.data.series.datasource.local.SeriesLocalDataSource
import com.didik.moflix.data.series.datasource.local.SeriesLocalDataSourceImpl
import com.didik.moflix.data.series.datasource.local.room.SeriesDao
import com.didik.moflix.data.series.datasource.remote.SeriesRemoteDataSource
import com.didik.moflix.data.series.datasource.remote.SeriesRemoteDataSourceImpl
import com.didik.moflix.data.series.mapper.SeriesMapper
import com.didik.moflix.data.series.repository.SeriesRepositoryImpl
import com.didik.moflix.domain.repository.SeriesRepository
import com.didik.moflix.domain.usecase.SeriesUseCase
import com.didik.moflix.utils.dispatcher.DispatchersProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class SeriesModule {

    @Provides
    fun provideSeriesRemoteDataSource(apiServices: ApiServices): SeriesRemoteDataSource {
        return SeriesRemoteDataSourceImpl(apiServices)
    }

    @Provides
    fun provideSeriesDao(appDatabase: AppDatabase): SeriesDao = appDatabase.seriesDao()

    @Provides
    fun provideSeriesLocalDataSource(seriesDao: SeriesDao): SeriesLocalDataSource {
        return SeriesLocalDataSourceImpl(seriesDao)
    }

    @Provides
    fun provideSeriesMapper(): SeriesMapper = SeriesMapper()

    @Provides
    fun provideSeriesRepository(
        remoteDataSource: SeriesRemoteDataSource,
        localDataSource: SeriesLocalDataSource,
        mapper: SeriesMapper
    ): SeriesRepository {
        return SeriesRepositoryImpl(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
            mapper = mapper
        )
    }

    @Provides
    fun provideSeriesUseCase(
        repository: SeriesRepository,
        dispatchers: DispatchersProvider
    ) = SeriesUseCase(repository, dispatchers)
}