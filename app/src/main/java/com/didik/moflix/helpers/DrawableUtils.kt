package com.didik.moflix.helpers

import android.content.res.ColorStateList

object DrawableUtils {
    val default get() = intArrayOf()
    val checked get() = intArrayOf(android.R.attr.state_checked)

    fun colorStateList(vararg colorStates: Pair<IntArray, Int>): ColorStateList {
        return ColorStateList(
            colorStates.map { it.first }.toTypedArray(),
            colorStates.map { it.second }.toIntArray()
        )
    }
}