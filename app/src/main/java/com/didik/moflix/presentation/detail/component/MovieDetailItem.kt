package com.didik.moflix.presentation.detail.component

import android.view.View
import com.didik.moflix.R
import com.didik.moflix.databinding.ItemMovieDetailBinding
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.utils.extensions.setupRatingDrawable
import com.xwray.groupie.viewbinding.BindableItem

class MovieDetailItem(private val movie: MovieModel) : BindableItem<ItemMovieDetailBinding>() {

    override fun getLayout(): Int = R.layout.item_movie_detail

    override fun initializeViewBinding(view: View): ItemMovieDetailBinding =
        ItemMovieDetailBinding.bind(view)

    override fun bind(viewBinding: ItemMovieDetailBinding, position: Int) {
        with(viewBinding) {
            titleTextView.text = movie.title
            releaseDateTextView.text = movie.releaseDate
            ratingTextView.text = movie.ratingText
            overviewTextView.text = movie.overview

            ratingBar.run {
                setupRatingDrawable()
                rating = movie.rating
            }
        }
    }
}