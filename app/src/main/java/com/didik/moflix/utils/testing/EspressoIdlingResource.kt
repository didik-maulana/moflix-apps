package com.didik.moflix.utils.testing

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResource {

    private const val RESOURCE = "global"
    private val countingIdlingResource = CountingIdlingResource(RESOURCE)

    val idlingResource: IdlingResource
        get() = countingIdlingResource

    val isNotIdleNow: Boolean
        get() = idlingResource.isIdleNow.not()

    fun start() = countingIdlingResource.increment()

    fun end() = countingIdlingResource.decrement()

}