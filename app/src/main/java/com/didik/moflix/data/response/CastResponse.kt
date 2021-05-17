package com.didik.moflix.data.response

import com.squareup.moshi.Json

data class CastResponse(
    @Json(name = "name")
    val name: String? = null,

    @Json(name = "profile_path")
    val profilePath: String? = null,

    @Json(name = "character")
    val character: String? = null
)