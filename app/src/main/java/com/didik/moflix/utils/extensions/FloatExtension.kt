package com.didik.moflix.utils.extensions

fun Float?.toRatingFormat(): Float {
    return (this ?: 0f) / 2f
}

fun Float?.toRatingText(): String {
    return String.format("%.1f", this.toRatingFormat())
}