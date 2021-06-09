package com.didik.moflix.utils.helpers

import androidx.sqlite.db.SimpleSQLiteQuery

object FavoriteSortUtils {

    enum class Sort(val value: String) {
        NEWEST("ORDER BY created_at DESC"),
        OLDEST("ORDER BY created_at ASC"),
        RANDOM("ORDER BY RANDOM()")
    }

    fun getMovieSortedQuery(
        filter: Sort
    ): SimpleSQLiteQuery {
        val sortQuery = "SELECT * FROM movies ${filter.value}"
        return SimpleSQLiteQuery(sortQuery)
    }

    fun getSeriesSortedQuery(
        filter: Sort
    ): SimpleSQLiteQuery {
        val sortQuery = "SELECT * FROM series ${filter.value}"
        return SimpleSQLiteQuery(sortQuery)
    }
}