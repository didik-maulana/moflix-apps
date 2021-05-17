package com.didik.moflix.data.series.mapper

import com.didik.moflix.data.series.datasource.remote.response.SeriesResponse
import com.didik.moflix.domain.model.CastModel
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.network.helper.ImageHelper
import com.didik.moflix.network.helper.ImageSize
import com.didik.moflix.utils.extensions.formatReleaseDate
import com.didik.moflix.utils.extensions.toRatingFormat
import com.didik.moflix.utils.extensions.toRatingText

class SeriesMapper {

    fun mapToDomain(series: SeriesResponse): MovieModel {
        return with(series) {
            MovieModel(
                id = series.id ?: 0,
                title = name.orEmpty(),
                backdropUrl = ImageHelper.getImageURL(ImageSize.MEDIUM, backdropPath.orEmpty()),
                thumbnailUrl = ImageHelper.getImageURL(ImageSize.SMALL, posterPath.orEmpty()),
                releaseDate = firstAirDate.formatReleaseDate(),
                rating = voteAverage.toRatingFormat(),
                ratingText = voteAverage.toRatingText(),
                overview = overview.orEmpty(),
                cast = credits?.cast?.filter {
                    it.profilePath.isNullOrBlank().not()
                }?.map { cast ->
                    CastModel(
                        photoUrl = ImageHelper.getImageURL(
                            ImageSize.SMALL,
                            cast.profilePath.orEmpty()
                        ),
                        name = cast.name.orEmpty(),
                        character = cast.character.orEmpty()
                    )
                }.orEmpty()
            )
        }
    }

    fun mapToListDomain(seriesList: List<SeriesResponse>): List<MovieModel> {
        return seriesList.map { mapToDomain(it) }
    }
}