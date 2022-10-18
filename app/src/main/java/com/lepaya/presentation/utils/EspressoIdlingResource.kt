package com.lepaya.presentation.utils

import androidx.test.espresso.idling.CountingIdlingResource

//Idling resource will use for background process in AndroidTest by Espresso
//Better way than Sleep(). Weird to put test codes in production but advised by Google
object EspressoIdlingResource {
    private const val RESOURCE = "GLOBAL"

    @JvmField
    val countingIdlingResource = CountingIdlingResource(RESOURCE)

    fun increment() {
        countingIdlingResource.increment()
    }

    fun decrement() {
        if (!countingIdlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }

}