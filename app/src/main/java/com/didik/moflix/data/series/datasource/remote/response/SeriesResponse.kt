package com.didik.moflix.data.series.datasource.remote.response

import com.didik.moflix.data.response.CreditsResponse
import com.squareup.moshi.Json

data class SeriesResponse(
    @Json(name = "id")
    val id: Int? = null,

    @Json(name = "name")
    val name: String? = null,

    @Json(name = "backdrop_path")
    val backdropPath: String? = null,

    @Json(name = "poster_path")
    val posterPath: String? = null,

    @Json(name = "first_air_date")
    val firstAirDate: String? = null,

    @Json(name = "vote_average")
    val voteAverage: Float? = null,

    @Json(name = "overview")
    val overview: String? = null,

    @Json(name = "credits")
    val credits: CreditsResponse? = null
)