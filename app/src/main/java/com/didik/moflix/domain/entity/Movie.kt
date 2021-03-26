package com.didik.moflix.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val title: String,
    val backdropUrl: String,
    val thumbnailUrl: String,
    val releaseDate: String,
    val rating: Float,
    val ratingText: String,
    val overview: String
) : Parcelable