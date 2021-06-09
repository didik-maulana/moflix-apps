package com.didik.moflix.presentation.features.movies.detail

import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.didik.moflix.R
import com.didik.moflix.base.BindingActivity
import com.didik.moflix.databinding.ActivityMovieDetailBinding
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.presentation.components.CoverItem
import com.didik.moflix.presentation.components.CreditsItem
import com.didik.moflix.presentation.components.MovieDetailItem
import com.didik.moflix.utils.extensions.observeData
import com.didik.moflix.utils.extensions.shareContent
import com.didik.moflix.utils.helpers.ColorPalette
import com.didik.moflix.utils.testing.EspressoIdlingResource
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

    private var menu: Menu? = null

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
        viewModel.movieId = intent.getIntExtra(EXTRA_MOVIE_ID, 0)
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
        menuInflater.inflate(R.menu.detail_menu, menu)
        this.menu = menu

        viewModel.isFavorite.observeData(this) { isFavorite ->
            toggleFavorite(isFavorite)

            if (viewModel.isFirstLoad) {
                renderOptionsMenu(true)
                viewModel.isFirstLoad = false
            }
        }
        return true
    }

    private fun toggleFavorite(isFavorite: Boolean) {
        val menuItem = menu?.findItem(R.id.favorite) ?: return

        menuItem.icon = if (isFavorite) {
            ContextCompat.getDrawable(this, R.drawable.ic_filled_favorite)
        } else {
            ContextCompat.getDrawable(this, R.drawable.ic_outline_favorite)
        }
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
            R.id.favorite -> {
                val isFavorite = viewModel.isFavorite.value ?: false
                if (isFavorite) {
                    viewModel.deleteMovie()
                    toast(getString(R.string.msg_success_removed_from_favorites))
                } else {
                    viewModel.insertMovie()
                    toast(getString(R.string.msg_success_added_to_favorites))
                }
                viewModel.checkFavoriteMovie()
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
            shareContent(movieText)
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
        EspressoIdlingResource.start()
        viewModel.loadMovieDetail()
    }

    private fun setupObserver() {
        viewModel.movie.observeData(this) { movie ->
            viewModel.checkFavoriteMovie()
            renderMovieDetail(movie)
        }

        viewModel.isLoading.observeData(this) { isLoading ->
            renderLoading(isLoading)
        }

        viewModel.error.observeData(this) { error ->
            toast(error)
            renderOptionsMenu(false)
        }
    }

    private fun renderOptionsMenu(isShown: Boolean) {
        menu?.findItem(R.id.share)?.isVisible = isShown
        menu?.findItem(R.id.favorite)?.isVisible = isShown
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

        if (EspressoIdlingResource.isNotIdleNow) {
            EspressoIdlingResource.end()
        }
    }

    companion object {
        private const val EXTRA_MOVIE_ID = "extra_movie_id"

        fun createIntent(
            context: Context,
            movieId: Int? = null,
        ): Intent {
            return Intent(context, MovieDetailActivity::class.java).apply {
                putExtra(EXTRA_MOVIE_ID, movieId)
            }
        }
    }
}