package com.didik.moflix.presentation.detail

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.annotation.ColorInt
import coil.load
import coil.transform.RoundedCornersTransformation
import com.didik.moflix.R
import com.didik.moflix.core.BindingActivity
import com.didik.moflix.databinding.ActivityMovieDetailBinding
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.utils.extensions.observeData
import com.didik.moflix.utils.extensions.setupRatingDrawable
import com.didik.moflix.utils.helpers.ColorPalette
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class MovieDetailActivity : BindingActivity<ActivityMovieDetailBinding>(), CoroutineScope {

    private val job: Job by lazy { Job() }
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    private val viewModel: MovieDetailViewModel by viewModels()

    override fun initViewBinding(inflater: LayoutInflater): ActivityMovieDetailBinding {
        return ActivityMovieDetailBinding.inflate(inflater)
    }

    override fun renderView() {
        setupObserver()
        setupActionBar()
        setupAnimatedToolbar()
        viewModel.loadMovie(intent?.getParcelableExtra(EXTRA_MOVIE))
    }

    override fun onDestroy() {
        job.cancel()
        super.onDestroy()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.movie_detail_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            R.id.share -> {
                shareMovie()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun shareMovie() {
        viewModel.movie.value?.let { movie ->
            val movieText = StringBuilder().apply {
                append(movie.title)
                append("\n\n")
                append(movie.overview)
            }.toString()

            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = TEXT_PLAIN_TYPE
                putExtra(Intent.EXTRA_TEXT, movieText)
            }

            startActivity(
                Intent.createChooser(
                    shareIntent,
                    getString(R.string.label_share)
                )
            )
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.movieDetailToolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
    }

    private fun setupAnimatedToolbar() {
        binding.movieDetailScrollView.viewTreeObserver.addOnScrollChangedListener {
            launch(Dispatchers.Main) {
                val scrollPosition = binding.movieDetailScrollView.scrollY
                val maxPosition = binding.coverImageView.measuredHeight

                if (scrollPosition > maxPosition) {
                    binding.movieDetailToolbar.title = viewModel.movie.value?.title
                    setToolbarColor(ColorPalette.IMPERIAL_PRIME)
                } else {
                    binding.movieDetailToolbar.title = null
                    setToolbarColor(Color.TRANSPARENT)
                }
            }
        }
    }

    private fun setToolbarColor(@ColorInt color: Int) {
        val backgroundDrawable = binding.movieDetailToolbar.background as? ColorDrawable
        val currentColor = backgroundDrawable?.color

        if (currentColor != color) {
            binding.movieDetailToolbar.setBackgroundColor(color)
        }
    }

    private fun setupObserver() {
        viewModel.movie.observeData(this, { movie ->
            setupUI(movie)
        })
    }

    private fun setupUI(movieModel: MovieModel) {
        binding.coverImageView.load(movieModel.backdropUrl) {
            crossfade(true)
            crossfade(500)
            placeholder(R.drawable.ic_movie_24)
            error(R.drawable.ic_movie_24)
        }

        binding.thumbnailImageView.load(movieModel.thumbnailUrl) {
            crossfade(true)
            crossfade(500)
            transformations(RoundedCornersTransformation(10f))
            placeholder(R.drawable.ic_movie_24)
            error(R.drawable.ic_movie_24)
        }

        binding.titleTextView.text = movieModel.title
        binding.releaseDateTextView.text = movieModel.releaseDate
        binding.ratingTextView.text = movieModel.ratingText
        binding.overviewTextView.text = movieModel.overview

        binding.ratingBar.run {
            setupRatingDrawable()
            rating = movieModel.rating
        }
    }

    companion object {
        private const val TEXT_PLAIN_TYPE = "text/plain"
        private const val EXTRA_MOVIE = "extra_movie"

        fun newIntent(context: Context, movieModel: MovieModel?): Intent {
            return Intent(context, MovieDetailActivity::class.java).apply {
                putExtra(EXTRA_MOVIE, movieModel)
            }
        }
    }
}