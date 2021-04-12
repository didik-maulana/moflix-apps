package com.didik.moflix.data.movies.datasource.remote.response

import com.squareup.moshi.Json

data class MovieListResponse(
    @Json(name = "results")
    val results: List<MovieResponse>? = null
)