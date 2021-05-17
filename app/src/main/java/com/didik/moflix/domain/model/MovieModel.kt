package com.didik.moflix.domain.model

data class MovieModel(
    val id: Int,
    val title: String,
    val backdropUrl: String,
    val thumbnailUrl: String,
    val releaseDate: String,
    val rating: Float,
    val ratingText: String,
    val overview: String,
    val cast: List<CastModel>
)