package com.didik.moflix.data.series.mapper

import androidx.annotation.VisibleForTesting
import androidx.arch.core.util.Function
import com.didik.moflix.data.series.datasource.local.entities.SeriesEntity
import com.didik.moflix.data.series.datasource.remote.response.SeriesResponse
import com.didik.moflix.domain.model.CastModel
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.network.helper.ImageHelper
import com.didik.moflix.network.helper.ImageSize
import com.didik.moflix.utils.extensions.formatReleaseDate
import com.didik.moflix.utils.extensions.toRatingFormat
import com.didik.moflix.utils.extensions.toRatingText

class SeriesMapper {

    fun mapResponseToDomain(series: SeriesResponse): MovieModel {
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

    fun mapResponseToListDomain(seriesList: List<SeriesResponse>): List<MovieModel> {
        return seriesList.map(::mapResponseToDomain)
    }

    @VisibleForTesting
    fun mapEntityToDomain(entity: SeriesEntity): MovieModel {
        return with(entity) {
            MovieModel(
                id = this.id,
                title = title,
                backdropUrl = "",
                thumbnailUrl = thumbnailUrl,
                releaseDate = releaseDate,
                rating = rating,
                ratingText = ratingText,
                overview = "",
                cast = emptyList()
            )
        }
    }

    fun getMapperEntityToDomain(): Function<SeriesEntity, MovieModel> {
        return Function(::mapEntityToDomain)
    }

    fun mapDomainToEntity(model: MovieModel): SeriesEntity {
        return with(model) {
            SeriesEntity(
                id = this.id,
                title = title,
                thumbnailUrl = thumbnailUrl,
                releaseDate = releaseDate,
                rating = rating,
                ratingText = ratingText,
                createdAt = System.currentTimeMillis()
            )
        }
    }

}