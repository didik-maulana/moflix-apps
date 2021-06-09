package com.didik.moflix.presentation.features.series.detail

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
import com.didik.moflix.databinding.ActivitySeriesDetailBinding
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
class SeriesDetailActivity : BindingActivity<ActivitySeriesDetailBinding>(), CoroutineScope {

    private val job: Job by lazy { Job() }
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    private val viewModel: SeriesDetailViewModel by viewModels()

    private val seriesDetailAdapter by lazy { GroupieAdapter() }
    private val seriesDetailLayoutManager by lazy { LinearLayoutManager(this) }

    private var menu: Menu? = null

    private val recyclerViewListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val isVisibleCover = seriesDetailLayoutManager.findFirstVisibleItemPosition() != 0
            if (isVisibleCover) {
                binding.seriesDetailToolbar.title = viewModel.series.value?.title
                setToolbarColor(ColorPalette.GREY_SHARK)
            } else {
                binding.seriesDetailToolbar.title = null
                setToolbarColor(ColorPalette.TRANSPARENT)
            }
        }
    }

    override fun initViewBinding(inflater: LayoutInflater): ActivitySeriesDetailBinding {
        return ActivitySeriesDetailBinding.inflate(inflater)
    }

    override fun renderView() {
        viewModel.seriesId = intent.getIntExtra(EXTRA_SERIES_ID, 0)
        setupObserver()
        setupActionBar()
        setupAnimatedToolbar()
        setupSwipeRefresh()
        loadDetail()
    }

    override fun onDestroy() {
        job.cancel()
        binding.seriesDetailRecyclerView.removeOnScrollListener(recyclerViewListener)
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
                shareSeries()
                true
            }
            R.id.favorite -> {
                val isFavorite = viewModel.isFavorite.value ?: false
                if (isFavorite) {
                    viewModel.deleteSeries()
                    toast(getString(R.string.msg_success_removed_from_favorites))
                } else {
                    viewModel.insertSeries()
                    toast(getString(R.string.msg_success_added_to_favorites))
                }
                viewModel.checkFavoriteSeries()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun shareSeries() {
        viewModel.series.value?.let { series ->
            val seriesText = StringBuilder().apply {
                append(series.title)
                append("\n\n")
                append(series.overview)
            }.toString()
            shareContent(seriesText)
        }
    }

    private fun setupActionBar() {
        setSupportActionBar(binding.seriesDetailToolbar)
        supportActionBar?.run {
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowTitleEnabled(false)
        }
    }

    private fun setupAnimatedToolbar() {
        binding.seriesDetailRecyclerView.addOnScrollListener(recyclerViewListener)
    }

    private fun setToolbarColor(@ColorInt color: Int) {
        val backgroundDrawable = binding.seriesDetailToolbar.background as? ColorDrawable
        val currentColor = backgroundDrawable?.color

        if (currentColor != color) {
            binding.seriesDetailToolbar.setBackgroundColor(color)
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            loadDetail()
        }
    }

    private fun loadDetail() {
        EspressoIdlingResource.start()
        viewModel.loadSeriesDetail()
    }

    private fun setupObserver() {
        viewModel.series.observeData(this) { movie ->
            viewModel.checkFavoriteSeries()
            renderSeriesDetail(movie)
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
        menu?.findItem(R.id.favorite)?.isVisible = isShown
        menu?.findItem(R.id.share)?.isVisible = isShown
    }

    private fun renderLoading(isLoading: Boolean) {
        binding.swipeRefreshLayout.isRefreshing = isLoading
        binding.seriesDetailRecyclerView.isVisible = !isLoading
    }

    private fun renderSeriesDetail(series: MovieModel) {
        binding.seriesDetailRecyclerView.run {
            adapter = seriesDetailAdapter
            layoutManager = seriesDetailLayoutManager
        }

        seriesDetailAdapter.clear()
        seriesDetailAdapter.addAll(
            listOf(
                CoverItem(
                    coverUrl = series.backdropUrl,
                    thumbnailUrl = series.thumbnailUrl
                ),
                MovieDetailItem(series)
            )
        )

        if (series.cast.isNotEmpty()) {
            seriesDetailAdapter.add(
                CreditsItem(
                    title = getString(R.string.title_cast),
                    credits = series.cast
                )
            )
        }

        if (EspressoIdlingResource.isNotIdleNow) {
            EspressoIdlingResource.end()
        }
    }

    companion object {
        private const val EXTRA_SERIES_ID = "extra_series_id"

        fun createIntent(
            context: Context,
            seriesId: Int? = null
        ): Intent {
            return Intent(context, SeriesDetailActivity::class.java).apply {
                putExtra(EXTRA_SERIES_ID, seriesId)
            }
        }
    }
}