package com.didik.moflix.data.mapper

import com.didik.moflix.data.model.MovieModel
import com.didik.moflix.domain.entity.Movie
import com.didik.moflix.utils.extensions.formatReleaseDate
import com.didik.moflix.utils.extensions.toRatingFormat
import com.didik.moflix.utils.extensions.toRatingText

class MovieMapper {

    private fun mapToDomain(model: MovieModel): Movie {
        return with(model) {
            Movie(
                title = title.orEmpty(),
                backdropUrl = "https://image.tmdb.org/t/p/w400/" + backdropPath.orEmpty(),
                thumbnailUrl = "https://image.tmdb.org/t/p/w200/" + posterPath.orEmpty(),
                releaseDate = releaseDate.formatReleaseDate(),
                rating = voteAverage.toRatingFormat(),
                ratingText = voteAverage.toRatingText(),
                overview = overview.orEmpty()
            )
        }
    }

    fun mapToListDomain(listModel: List<MovieModel>): List<Movie> {
        return listModel.map { mapToDomain(it) }
    }
}