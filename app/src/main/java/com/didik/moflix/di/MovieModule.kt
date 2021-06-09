package com.didik.moflix.di

import com.didik.moflix.data.database.AppDatabase
import com.didik.moflix.data.movies.datasource.local.MovieLocalDataSource
import com.didik.moflix.data.movies.datasource.local.MovieLocalDataSourceImpl
import com.didik.moflix.data.movies.datasource.local.room.MovieDao
import com.didik.moflix.data.movies.datasource.remote.MovieRemoteDataSource
import com.didik.moflix.data.movies.datasource.remote.MovieRemoteDataSourceImpl
import com.didik.moflix.data.movies.mapper.MovieMapper
import com.didik.moflix.data.movies.repository.MovieRepositoryImpl
import com.didik.moflix.data.routes.ApiServices
import com.didik.moflix.domain.repository.MovieRepository
import com.didik.moflix.domain.usecase.MovieUseCase
import com.didik.moflix.utils.dispatcher.DispatchersProvider
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
    fun provideMovieDao(appDatabase: AppDatabase): MovieDao = appDatabase.movieDao()

    @Provides
    fun provideMovieLocalDataSource(movieDao: MovieDao): MovieLocalDataSource {
        return MovieLocalDataSourceImpl(movieDao)
    }

    @Provides
    fun provideMovieMapper(): MovieMapper = MovieMapper()

    @Provides
    fun provideMovieRepository(
        remoteDataSource: MovieRemoteDataSource,
        localDataSource: MovieLocalDataSource,
        mapper: MovieMapper
    ): MovieRepository {
        return MovieRepositoryImpl(
            remoteDataSource = remoteDataSource,
            localDataSource = localDataSource,
            mapper = mapper
        )
    }

    @Provides
    fun provideMovieUseCase(
        repository: MovieRepository,
        dispatchers: DispatchersProvider
    ) = MovieUseCase(repository, dispatchers)
}