package com.didik.moflix.utils.extensions

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun String?.formatReleaseDate(): String {
    val parser = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val formatter = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())

    return try {
        val dateParse = parser.parse(this.orEmpty())
        formatter.format(dateParse ?: Date())
    } catch (exception: ParseException) {
        this.orEmpty()
    }
}