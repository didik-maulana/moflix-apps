package com.didik.moflix.data.series.mapper

import androidx.annotation.VisibleForTesting
import com.didik.moflix.data.series.datasource.remote.response.SeriesResponse
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.network.helper.ImageHelper
import com.didik.moflix.network.helper.ImageSize
import com.didik.moflix.utils.extensions.formatReleaseDate
import com.didik.moflix.utils.extensions.toRatingFormat
import com.didik.moflix.utils.extensions.toRatingText

class SeriesMapper {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun mapToDomain(series: SeriesResponse): MovieModel {
        return with(series) {
            MovieModel(
                id = 0,
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