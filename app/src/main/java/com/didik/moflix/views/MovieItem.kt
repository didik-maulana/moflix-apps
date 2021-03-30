package com.didik.moflix.views

import android.view.View
import coil.load
import coil.transform.RoundedCornersTransformation
import com.didik.moflix.R
import com.didik.moflix.databinding.ItemMovieBinding
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.utils.extensions.setupRatingDrawable
import com.xwray.groupie.viewbinding.BindableItem

class MovieItem(
    private val movie: MovieModel,
    private val onItemClickListener: ((position: Int) -> Unit)
) : BindableItem<ItemMovieBinding>() {

    override fun getLayout(): Int = R.layout.item_movie

    override fun initializeViewBinding(view: View): ItemMovieBinding = ItemMovieBinding.bind(view)

    override fun bind(viewBinding: ItemMovieBinding, position: Int) {
        renderTextView(viewBinding)
        renderRatingBar(viewBinding)
        renderThumbnail(viewBinding)

        viewBinding.root.setOnClickListener {
            onItemClickListener.invoke(position)
        }
    }

    private fun renderThumbnail(viewBinding: ItemMovieBinding) {
        viewBinding.thumbnailImageView.load(movie.thumbnailUrl) {
            crossfade(true)
            crossfade(500)
            transformations(RoundedCornersTransformation(10f))
            placeholder(R.drawable.ic_movie_24)
            error(R.drawable.ic_movie_24)
        }
    }

    private fun renderTextView(viewBinding: ItemMovieBinding) {
        with(viewBinding) {
            titleTextView.text = movie.title
            releaseDateTextView.text = movie.releaseDate
            ratingTextView.text = movie.ratingText
        }
    }

    private fun renderRatingBar(viewBinding: ItemMovieBinding) {
        with(viewBinding) {
            ratingBar.setupRatingDrawable()
            ratingBar.rating = movie.rating
        }
    }
}