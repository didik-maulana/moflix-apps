package com.didik.moflix.utils.helpers

import androidx.sqlite.db.SimpleSQLiteQuery

object FavoriteSortUtils {

    enum class Type(val value: String) {
        MOVIES("movies"),
        SERIES("series")
    }

    enum class Sort(val value: String) {
        NEWEST("ORDER BY created_at DESC"),
        OLDEST("ORDER BY created_at ASC"),
        RANDOM("ORDER BY RANDOM()")
    }

    fun getSortedQuery(
        type: Type,
        filter: Sort
    ): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().apply {
            append("SELECT * FROM ${type.value} ")
            append(filter.value)
        }.toString()
        return SimpleSQLiteQuery(simpleQuery)
    }
}