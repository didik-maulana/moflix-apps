package com.didik.moflix.di

import com.didik.moflix.utils.dispatcher.AppDispatchers
import com.didik.moflix.utils.dispatcher.DispatchersProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DispatchersModule {

    @Provides
    fun provideAppDispatchers(): DispatchersProvider = AppDispatchers()

}