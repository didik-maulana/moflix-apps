package com.didik.moflix.di

import com.didik.moflix.data.routes.ApiServices
import com.didik.moflix.network.Network
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideApiServices(): ApiServices = Network.retrofit().create(ApiServices::class.java)
}