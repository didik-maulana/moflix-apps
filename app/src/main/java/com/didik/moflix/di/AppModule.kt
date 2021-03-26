package com.didik.moflix.di

import com.didik.moflix.data.datasource.MovieLocalDataSource
import com.didik.moflix.data.datasource.MovieLocalDataSourceImpl
import com.didik.moflix.data.datasource.SeriesLocalDataSource
import com.didik.moflix.data.datasource.SeriesLocalDataSourceImpl
import com.didik.moflix.data.mapper.MovieMapper
import com.didik.moflix.data.mapper.SeriesMapper
import com.didik.moflix.data.repository.MovieRepositoryImpl
import com.didik.moflix.data.repository.SeriesRepositoryImpl
import com.didik.moflix.domain.repository.MovieRepository
import com.didik.moflix.domain.repository.SeriesRepository
import com.didik.moflix.domain.usecase.GetMoviesUseCase
import com.didik.moflix.domain.usecase.GetSeriesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideMovieLocalDataSource(): MovieLocalDataSource = MovieLocalDataSourceImpl()

    @Provides
    @Singleton
    fun provideMovieMapper(): MovieMapper = MovieMapper()

    @Provides
    @Singleton
    fun provideMovieRepository(
        localDataSource: MovieLocalDataSource,
        mapper: MovieMapper
    ): MovieRepository = MovieRepositoryImpl(localDataSource, mapper)

    @Provides
    @Singleton
    fun provideGetMovieUseCase(repository: MovieRepository) = GetMoviesUseCase(repository)

    @Provides
    @Singleton
    fun provideSeriesLocalDataSource(): SeriesLocalDataSource = SeriesLocalDataSourceImpl()

    @Provides
    @Singleton
    fun provideSeriesMapper(): SeriesMapper = SeriesMapper()

    @Provides
    @Singleton
    fun provideSeriesRepository(
        localDataSource: SeriesLocalDataSource,
        mapper: SeriesMapper
    ): SeriesRepository = SeriesRepositoryImpl(localDataSource, mapper)

    @Provides
    @Singleton
    fun provideGetSeriesUseCase(repository: SeriesRepository) = GetSeriesUseCase(repository)
}