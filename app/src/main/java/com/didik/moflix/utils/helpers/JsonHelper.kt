package com.didik.moflix.utils.helpers

import android.content.Context
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import java.io.InputStream

object JsonHelper {

    enum class Assets(val fileName: String) {
        MOVIES("movies"),
        SERIES("series")
    }

    inline fun <reified T : Any> readDataFromJson(context: Context, assets: Assets): T? {
        val json: String?

        json = try {
            val inputStream: InputStream = context.assets.open("${assets.fileName}.json")
            inputStream.bufferedReader().use { it.readText() }
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }

        return if (json.isNullOrBlank()) {
            null
        } else {
            val moshiBuilder = Moshi.Builder().apply {
                add(KotlinJsonAdapterFactory())
            }.build()

            val jsonAdapter: JsonAdapter<T> = moshiBuilder.adapter(T::class.java)
            jsonAdapter.fromJson(json)
        }
    }
}