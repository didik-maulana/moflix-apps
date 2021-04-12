package com.didik.moflix.di

import com.didik.moflix.data.movies.datasource.remote.MovieRemoteDataSource
import com.didik.moflix.data.movies.datasource.remote.MovieRemoteDataSourceImpl
import com.didik.moflix.data.movies.mapper.MovieMapper
import com.didik.moflix.data.movies.repository.MovieRepositoryImpl
import com.didik.moflix.domain.repository.MovieRepository
import com.didik.moflix.domain.usecase.GetMoviesUseCase
import com.didik.moflix.presentation.movies.MoviesViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class MoviesModule {

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
    fun provideMoviesViewModel(getMoviesUseCase: GetMoviesUseCase) = MoviesViewModel(getMoviesUseCase)
}