package com.didik.moflix.data.movies.mapper

import androidx.annotation.VisibleForTesting
import com.didik.moflix.data.movies.datasource.local.entities.MovieEntity
import com.didik.moflix.data.movies.datasource.remote.response.MovieResponse
import com.didik.moflix.domain.model.CastModel
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.network.helper.ImageHelper
import com.didik.moflix.network.helper.ImageSize
import com.didik.moflix.utils.extensions.formatReleaseDate
import com.didik.moflix.utils.extensions.toRatingFormat
import com.didik.moflix.utils.extensions.toRatingText

class MovieMapper {

    fun mapResponseToDomain(response: MovieResponse): MovieModel {
        return with(response) {
            MovieModel(
                id = id ?: 0,
                title = title.orEmpty(),
                backdropUrl = ImageHelper.getImageURL(ImageSize.MEDIUM, backdropPath.orEmpty()),
                thumbnailUrl = ImageHelper.getImageURL(ImageSize.SMALL, posterPath.orEmpty()),
                releaseDate = releaseDate.formatReleaseDate(),
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

    fun mapResponseToListDomain(listResponse: List<MovieResponse>): List<MovieModel> {
        return listResponse.map { movieResponse ->
            mapResponseToDomain(movieResponse)
        }
    }

    @VisibleForTesting
    fun mapEntityToDomain(entity: MovieEntity): MovieModel {
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

    fun mapEntityToListDomain(listEntity: List<MovieEntity>): List<MovieModel> {
        return listEntity.map { movieEntity ->
            mapEntityToDomain(movieEntity)
        }
    }

    fun mapDomainToEntity(model: MovieModel): MovieEntity {
        return with(model) {
            MovieEntity(
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