package com.didik.moflix.di

import com.didik.moflix.data.movies.datasource.remote.MovieRemoteDataSource
import com.didik.moflix.data.movies.datasource.remote.MovieRemoteDataSourceImpl
import com.didik.moflix.data.movies.mapper.MovieMapper
import com.didik.moflix.data.movies.repository.MovieRepositoryImpl
import com.didik.moflix.data.routes.ApiServices
import com.didik.moflix.domain.repository.MovieRepository
import com.didik.moflix.domain.usecase.MovieUseCase
import com.didik.moflix.domain.usecase.SeriesUseCase
import com.didik.moflix.presentation.detail.MovieDetailViewModel
import com.didik.moflix.presentation.movies.MovieViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class MovieModule {

    @Provides
    fun provideMovieRemoteDataSource(apiServices: ApiServices): MovieRemoteDataSource {
        return MovieRemoteDataSourceImpl(apiServices)
    }

    @Provides
    fun provideMovieMapper(): MovieMapper = MovieMapper()

    @Provides
    fun provideMovieRepository(
        remoteDataSource: MovieRemoteDataSource,
        mapper: MovieMapper
    ): MovieRepository = MovieRepositoryImpl(remoteDataSource, mapper)

    @Provides
    fun provideMovieUseCase(repository: MovieRepository) = MovieUseCase(repository)

    @Provides
    fun provideMovieViewModel(
        movieUseCase: MovieUseCase
    ) = MovieViewModel(movieUseCase)

    @Provides
    fun provideMovieDetailViewModel(
        movieUseCase: MovieUseCase,
        seriesUseCase: SeriesUseCase
    ) = MovieDetailViewModel(movieUseCase, seriesUseCase)
}