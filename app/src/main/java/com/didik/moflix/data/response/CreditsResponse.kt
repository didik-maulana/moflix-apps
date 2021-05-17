package com.didik.moflix.data.response

import com.squareup.moshi.Json

data class CreditsResponse(
    @Json(name = "cast")
    val cast: List<CastResponse>? = null
)