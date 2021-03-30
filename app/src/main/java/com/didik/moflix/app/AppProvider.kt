package com.didik.moflix.app

import android.content.Context

object AppProvider {

    var context: Context? = null
        private set

    fun init(context: Context?) {
        this.context = context
    }

    fun clear() {
        context = null
    }
}