package com.didik.moflix.presentation.detail

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.annotation.ColorInt
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.didik.moflix.R
import com.didik.moflix.base.BindingActivity
import com.didik.moflix.databinding.ActivityMovieDetailBinding
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.presentation.detail.component.CoverItem
import com.didik.moflix.presentation.detail.component.CreditsItem
import com.didik.moflix.presentation.detail.component.MovieDetailItem
import com.didik.moflix.utils.extensions.observeData
import com.didik.moflix.utils.helpers.ColorPalette
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class MovieDetailActivity : BindingActivity<ActivityMovieDetailBinding>(), CoroutineScope {

    private val job: Job by lazy { Job() }
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    private val viewModel: MovieDetailViewModel by viewModels()

    private val movieDetailAdapter by lazy { GroupieAdapter() }
    private val movieDetailLayoutManager by lazy { LinearLayoutManager(this) }

    private val recyclerViewListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val isVisibleCover = movieDetailLayoutManager.findFirstVisibleItemPosition() != 0
            if (isVisibleCover) {
                binding.movieDetailToolbar.title = viewModel.movie.value?.title
                setToolbarColor(ColorPalette.GREY_SHARK)
            } else {
                binding.movieDetailToolbar.title = null
                setToolbarColor(ColorPalette.TRANSPARENT)
            }
        }
    }

    override fun initViewBinding(inflater: LayoutInflater): ActivityMovieDetailBinding {
        return ActivityMovieDetailBinding.inflate(inflater)
    }

    override fun renderView() {
        setupObserver()
        setupActionBar()
        setupAnimatedToolbar()
        setupSwipeRefresh()
        loadDetail()
    }

    override fun onDestroy() {
        job.cancel()
        binding.movieDetailRecyclerView.removeOnScrollListener(recyclerViewListener)
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
        binding.movieDetailRecyclerView.addOnScrollListener(recyclerViewListener)
    }

    private fun setToolbarColor(@ColorInt color: Int) {
        val backgroundDrawable = binding.movieDetailToolbar.background as? ColorDrawable
        val currentColor = backgroundDrawable?.color

        if (currentColor != color) {
            binding.movieDetailToolbar.setBackgroundColor(color)
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            loadDetail()
        }
    }

    private fun loadDetail() {
        intent?.run {
            val movieId = getIntExtra(EXTRA_MOVIE_ID, 0)
            val seriesId = getIntExtra(EXTRA_SERIES_ID, 0)

            when {
                movieId != 0 -> {
                    viewModel.loadMovieDetail(movieId)
                }
                seriesId != 0 -> {
                    viewModel.loadSeriesDetail(seriesId)
                }
                else -> onBackPressed()
            }
        }
    }

    private fun setupObserver() {
        viewModel.movie.observeData(this) { movie ->
            renderMovieDetail(movie)
        }

        viewModel.isLoading.observeData(this) { isLoading ->
            renderLoading(isLoading)
        }

        viewModel.error.observeData(this) { error ->
            toast(error)
        }
    }

    private fun renderLoading(isLoading: Boolean) {
        binding.swipeRefreshLayout.isRefreshing = isLoading
        binding.movieDetailRecyclerView.isVisible = !isLoading
    }

    private fun renderMovieDetail(movieModel: MovieModel) {
        binding.movieDetailRecyclerView.run {
            adapter = movieDetailAdapter
            layoutManager = movieDetailLayoutManager
        }

        movieDetailAdapter.clear()
        movieDetailAdapter.addAll(
            listOf(
                CoverItem(
                    coverUrl = movieModel.backdropUrl,
                    thumbnailUrl = movieModel.thumbnailUrl
                ),
                MovieDetailItem(movieModel)
            )
        )

        if (movieModel.cast.isNotEmpty()) {
            movieDetailAdapter.add(
                CreditsItem(
                    title = getString(R.string.title_cast),
                    credits = movieModel.cast
                )
            )
        }
    }

    companion object {
        private const val TEXT_PLAIN_TYPE = "text/plain"
        private const val EXTRA_MOVIE_ID = "extra_movie_id"
        private const val EXTRA_SERIES_ID = "extra_series_id"

        fun createIntent(
            context: Context,
            movieId: Int? = null,
            seriesId: Int? = null
        ): Intent {
            return Intent(context, MovieDetailActivity::class.java).apply {
                putExtra(EXTRA_MOVIE_ID, movieId)
                putExtra(EXTRA_SERIES_ID, seriesId)
            }
        }
    }
}