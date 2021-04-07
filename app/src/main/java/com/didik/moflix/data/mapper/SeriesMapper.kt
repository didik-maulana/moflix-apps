package com.didik.moflix.data.mapper

import com.didik.moflix.data.response.SeriesResponse
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.utils.extensions.formatReleaseDate
import com.didik.moflix.utils.extensions.toRatingFormat
import com.didik.moflix.utils.extensions.toRatingText
import com.didik.moflix.network.helper.ImageHelper
import com.didik.moflix.network.helper.ImageSize

class SeriesMapper {

    private fun mapToDomain(series: SeriesResponse): MovieModel {
        return with(series) {
            MovieModel(
                title = name.orEmpty(),
                backdropUrl = ImageHelper.getImageURL(ImageSize.MEDIUM, backdropPath.orEmpty()),
                thumbnailUrl = ImageHelper.getImageURL(ImageSize.SMALL, posterPath.orEmpty()),
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