package com.didik.moflix.utils.extensions

import android.content.Context
import android.widget.Toast

fun Context?.toast(
    message: String,
    duration: Int = Toast.LENGTH_SHORT
) {
    if (this != null) {
        Toast.makeText(this, message, duration).show()
    }
}