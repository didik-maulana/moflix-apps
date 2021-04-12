package com.didik.moflix.data.movies.mapper

import androidx.annotation.VisibleForTesting
import com.didik.moflix.data.movies.datasource.remote.response.MovieResponse
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.network.helper.ImageHelper
import com.didik.moflix.network.helper.ImageSize
import com.didik.moflix.utils.extensions.formatReleaseDate
import com.didik.moflix.utils.extensions.toRatingFormat
import com.didik.moflix.utils.extensions.toRatingText

class MovieMapper {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun mapToDomain(response: MovieResponse): MovieModel {
        return with(response) {
            MovieModel(
                title = title.orEmpty(),
                backdropUrl = ImageHelper.getImageURL(ImageSize.MEDIUM, backdropPath.orEmpty()),
                thumbnailUrl = ImageHelper.getImageURL(ImageSize.SMALL, posterPath.orEmpty()),
                releaseDate = releaseDate.formatReleaseDate(),
                rating = voteAverage.toRatingFormat(),
                ratingText = voteAverage.toRatingText(),
                overview = overview.orEmpty()
            )
        }
    }

    fun mapToListDomain(listResponse: List<MovieResponse>): List<MovieModel> {
        return listResponse.map { mapToDomain(it) }
    }
}