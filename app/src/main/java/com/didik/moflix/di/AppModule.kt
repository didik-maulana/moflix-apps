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
import com.didik.moflix.presentation.movies.MoviesViewModel
import com.didik.moflix.presentation.series.SeriesViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class AppModule {

    @Provides
    fun provideMovieLocalDataSource(): MovieLocalDataSource = MovieLocalDataSourceImpl()

    @Provides
    fun provideMovieMapper(): MovieMapper = MovieMapper()

    @Provides
    fun provideMovieRepository(
        localDataSource: MovieLocalDataSource,
        mapper: MovieMapper
    ): MovieRepository = MovieRepositoryImpl(localDataSource, mapper)

    @Provides
    fun provideGetMovieUseCase(repository: MovieRepository) = GetMoviesUseCase(repository)

    @Provides
    fun provideSeriesLocalDataSource(): SeriesLocalDataSource = SeriesLocalDataSourceImpl()

    @Provides
    fun provideSeriesMapper(): SeriesMapper = SeriesMapper()

    @Provides
    fun provideSeriesRepository(
        localDataSource: SeriesLocalDataSource,
        mapper: SeriesMapper
    ): SeriesRepository = SeriesRepositoryImpl(localDataSource, mapper)

    @Provides
    fun provideGetSeriesUseCase(repository: SeriesRepository) = GetSeriesUseCase(repository)

    @Provides
    fun provideMoviesViewModel(getMoviesUseCase: GetMoviesUseCase) = MoviesViewModel(getMoviesUseCase)

    @Provides
    fun provideSeriesViewModel(getSeriesUseCase: GetSeriesUseCase) = SeriesViewModel(getSeriesUseCase)
}