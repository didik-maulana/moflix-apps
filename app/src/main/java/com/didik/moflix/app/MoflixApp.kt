package com.didik.moflix.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MoflixApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AppProvider.init(this)
    }

    override fun onTerminate() {
        AppProvider.clear()
        super.onTerminate()
    }
}