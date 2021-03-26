package com.didik.moflix.app

import android.content.Context
import com.didik.moflix.BuildConfig

object AppProvider {

    lateinit var context: Context
        private set

    var isDebugMode: Boolean = true
        private set

    fun init(context: Context) {
        this.context = context
        this.isDebugMode = BuildConfig.DEBUG
    }
}