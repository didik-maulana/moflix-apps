package com.didik.moflix.utils.helpers

import com.didik.moflix.app.AppProvider
import com.didik.moflix.data.response.MovieListResponse
import com.didik.moflix.data.response.SeriesListResponse
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.InputStream

object JsonHelper {

    private const val MOVIES_FILE = "movies.json"
    private const val SERIES_FILE = "series.json"

    private val moshiBuilder: Moshi by lazy {
        Moshi.Builder().apply {
            add(KotlinJsonAdapterFactory())
        }.build()
    }

    fun readMoviesJson(): MovieListResponse? {
        val json: String?

        json = try {
            val inputStream: InputStream? = AppProvider.context?.assets?.open(MOVIES_FILE)
            inputStream?.bufferedReader()?.use { it.readText() }
        } catch (exception: Exception) {
            exception.printStackTrace()
            null
        }

        return if (json.isNullOrBlank()) {
            null
        } else {
            moshiBuilder
                .adapter(MovieListResponse::class.java)
                .fromJson(json)
        }
    }

    fun readSeriesJson(): SeriesListResponse? {
        val json: String?

        json = try {
            val inputStream: InputStream? = AppProvider.context?.assets?.open(SERIES_FILE)
            inputStream?.bufferedReader()?.use { it.readText() }
        } catch (exception: Exception) {
            exception.printStackTrace()
            null
        }

        return if (json.isNullOrBlank()) {
            null
        } else {
            moshiBuilder
                .adapter(SeriesListResponse::class.java)
                .fromJson(json)
        }
    }
}