package com.didik.moflix.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.didik.moflix.R
import com.didik.moflix.databinding.ItemMovieBinding
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.utils.extensions.setupRatingDrawable

class MovieAdapter : PagedListAdapter<MovieModel, MovieAdapter.MovieViewHolder>(diffCallback) {

    private var onItemClickListener: ((itemId: Int) -> Unit)? = null

    fun setOnItemClickListener(onItemClickListener: (itemId: Int) -> Unit) {
        this.onItemClickListener = onItemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val itemBinding = ItemMovieBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie != null) {
            holder.bind(movie)
        }
    }

    inner class MovieViewHolder(
        private val binding: ItemMovieBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: MovieModel) {
            with(movie) {
                renderThumbnail(thumbnailUrl)
                renderTextView(this)
                renderRatingBar(rating)

                binding.root.setOnClickListener {
                    onItemClickListener?.invoke(id)
                }
            }
        }

        private fun renderThumbnail(thumbnailUrl: String) {
            binding.thumbnailImageView.load(thumbnailUrl) {
                crossfade(true)
                crossfade(500)
                transformations(RoundedCornersTransformation(10f))
                placeholder(R.drawable.ic_movie)
                error(R.drawable.ic_movie)
            }
        }

        private fun renderTextView(movie: MovieModel) {
            with(binding) {
                titleTextView.text = movie.title
                releaseDateTextView.text = movie.releaseDate
                ratingTextView.text = movie.ratingText
            }
        }

        private fun renderRatingBar(rating: Float) {
            with(binding) {
                ratingBar.setupRatingDrawable()
                ratingBar.rating = rating
            }
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<MovieModel>() {
            override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}