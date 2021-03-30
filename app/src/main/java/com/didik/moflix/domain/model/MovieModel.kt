package com.didik.moflix.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieModel(
    val title: String,
    val backdropUrl: String,
    val thumbnailUrl: String,
    val releaseDate: String,
    val rating: Float,
    val ratingText: String,
    val overview: String
) : Parcelable