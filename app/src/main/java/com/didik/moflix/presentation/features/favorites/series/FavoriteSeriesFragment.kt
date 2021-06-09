package com.didik.moflix.presentation.features.favorites.series

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.PagedList
import com.didik.moflix.R
import com.didik.moflix.base.BindingFragment
import com.didik.moflix.databinding.FragmentFavoriteSeriesBinding
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.presentation.adapter.MovieAdapter
import com.didik.moflix.presentation.dialog.SortAlertDialog
import com.didik.moflix.presentation.features.series.detail.SeriesDetailActivity
import com.didik.moflix.utils.extensions.observeData
import com.didik.moflix.utils.helpers.CustomItemDecoration
import com.didik.moflix.utils.helpers.FavoriteSortUtils.Sort
import com.didik.moflix.utils.testing.EspressoIdlingResource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

@AndroidEntryPoint
class FavoriteSeriesFragment : BindingFragment<FragmentFavoriteSeriesBinding>(), CoroutineScope {

    private val job: Job by lazy { Job() }
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    private val viewModel: FavoriteSeriesViewModel by viewModels()
    private val seriesAdapter by lazy { MovieAdapter() }
    private var sortAlertDialog: SortAlertDialog? = null

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFavoriteSeriesBinding {
        return FragmentFavoriteSeriesBinding.inflate(inflater, container, false)
    }

    override fun renderView() {
        setupSeriesList()
        setupSeriesSort()
        loadFavoriteSeries(Sort.NEWEST)
    }

    override fun onDestroyView() {
        job.cancel()
        super.onDestroyView()
    }

    private fun setupSeriesList() {
        seriesAdapter.setOnItemClickListener { seriesId ->
            startActivity(
                SeriesDetailActivity.createIntent(
                    context = requireContext(),
                    seriesId = seriesId,
                )
            )
        }

        binding.favoriteSeriesRecyclerView.run {
            adapter = seriesAdapter
            addItemDecoration(
                CustomItemDecoration(
                    bottom = resources.getDimensionPixelSize(R.dimen.size_24dp)
                )
            )
        }
    }

    private fun setupSeriesSort() {
        sortAlertDialog = SortAlertDialog(requireContext()).apply {
            setTitle(getString(R.string.title_favorites_order))
            setOnItemSelectedListener { sort ->
                viewModel.isSortedList = true
                loadFavoriteSeries(sort)
            }
        }.create()

        binding.sortingButton.setOnClickListener {
            sortAlertDialog?.show()
        }
    }

    private fun loadFavoriteSeries(sort: Sort) {
        launch {
            EspressoIdlingResource.start()
            viewModel.getSeries(sort).observeData(viewLifecycleOwner) { series ->
                if (series.isNotEmpty()) {
                    renderSeriesList(series)
                    renderSortingButton(series.size > 1)
                } else {
                    renderEmptyState()
                    renderSortingButton(false)
                }

                if (EspressoIdlingResource.isNotIdleNow) {
                    EspressoIdlingResource.end()
                }
            }
        }
    }

    private fun renderSeriesList(series: PagedList<MovieModel>) {
        with(binding) {
            favoriteSeriesRecyclerView.isVisible = true
            emptyImageView.isVisible = false
            emptyTextView.isVisible = false
        }
        seriesAdapter.submitList(series)

        if (viewModel.isSortedList) {
            binding.favoriteSeriesRecyclerView.scrollToPosition(0)
            viewModel.isSortedList = false
        }
    }

    private fun renderEmptyState() {
        with(binding) {
            favoriteSeriesRecyclerView.isVisible = false
            emptyImageView.isVisible = true
            emptyTextView.isVisible = true
        }
    }

    private fun renderSortingButton(isShow: Boolean) {
        binding.sortingButton.isVisible = isShow
    }

}