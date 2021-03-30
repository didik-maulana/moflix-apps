package com.didik.moflix.data.mapper

import com.didik.moflix.data.response.SeriesResponse
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.utils.extensions.formatReleaseDate
import com.didik.moflix.utils.extensions.toRatingFormat
import com.didik.moflix.utils.extensions.toRatingText

class SeriesMapper {

    private fun mapToDomain(series: SeriesResponse): MovieModel {
        return with(series) {
            MovieModel(
                title = name.orEmpty(),
                backdropUrl = "https://image.tmdb.org/t/p/w400/" + backdropPath.orEmpty(),
                thumbnailUrl = "https://image.tmdb.org/t/p/w200/" + posterPath.orEmpty(),
                releaseDate = firstAirDate.formatReleaseDate(),
                rating = voteAverage.toRatingFormat(),
                ratingText = voteAverage.toRatingText(),
                overview = overview.orEmpty()
            )
        }
    }

    fun mapToListDomain(seriesList: List<SeriesResponse>): List<MovieModel> {
        return seriesList.map { mapToDomain(it) }
    }
}