package com.didik.moflix.data.response

import com.squareup.moshi.Json

data class SeriesResponse(
    val name: String? = null,

    @Json(name = "backdrop_path")
    val backdropPath: String? = null,

    @Json(name = "poster_path")
    val posterPath: String? = null,

    @Json(name = "first_air_date")
    val firstAirDate: String? = null,

    @Json(name = "vote_average")
    val voteAverage: Float? = null,

    val overview: String? = null
)