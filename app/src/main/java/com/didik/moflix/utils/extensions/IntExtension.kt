package com.didik.moflix.utils.extensions

import android.content.res.Resources
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.didik.moflix.app.MoflixApp

@ColorInt
fun Int.asColor(): Int {
    return try {
        ContextCompat.getColor(MoflixApp.applicationContext, this)
    } catch (exception: Resources.NotFoundException) {
        exception.printStackTrace()
        this
    }
}