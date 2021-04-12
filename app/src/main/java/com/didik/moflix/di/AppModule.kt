package com.didik.moflix.di

import com.didik.moflix.data.movies.datasource.remote.MovieRemoteDataSource
import com.didik.moflix.data.movies.datasource.remote.MovieRemoteDataSourceImpl
import com.didik.moflix.data.movies.mapper.MovieMapper
import com.didik.moflix.data.movies.repository.MovieRepositoryImpl
import com.didik.moflix.data.series.datasource.remote.SeriesRemoteDataSource
import com.didik.moflix.data.series.datasource.remote.SeriesRemoteDataSourceImpl
import com.didik.moflix.data.series.mapper.SeriesMapper
import com.didik.moflix.data.series.repository.SeriesRepositoryImpl
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
    fun provideMovieRemoteDataSource(): MovieRemoteDataSource = MovieRemoteDataSourceImpl()

    @Provides
    fun provideMovieMapper(): MovieMapper = MovieMapper()

    @Provides
    fun provideMovieRepository(
        remoteDataSource: MovieRemoteDataSource,
        mapper: MovieMapper
    ): MovieRepository = MovieRepositoryImpl(remoteDataSource, mapper)

    @Provides
    fun provideGetMovieUseCase(repository: MovieRepository) = GetMoviesUseCase(repository)

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
    fun provideMoviesViewModel(getMoviesUseCase: GetMoviesUseCase) =
        MoviesViewModel(getMoviesUseCase)

    @Provides
    fun provideSeriesViewModel(getSeriesUseCase: GetSeriesUseCase) =
        SeriesViewModel(getSeriesUseCase)
}