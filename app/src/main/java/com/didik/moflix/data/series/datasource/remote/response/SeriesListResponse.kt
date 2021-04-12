package com.didik.moflix.data.series.datasource.remote.response

import com.squareup.moshi.Json

data class SeriesListResponse(
    @Json(name = "results")
    val results: List<SeriesResponse>? = null
)