package com.didik.moflix.app

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MoflixApp : Application() {

    override fun onCreate() {
        super.onCreate()
        MoflixApp.applicationContext = applicationContext
    }

    companion object {
        lateinit var applicationContext: Context
    }
}