package com.didik.moflix.presentation.features.series.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.didik.moflix.R
import com.didik.moflix.base.BindingFragment
import com.didik.moflix.databinding.FragmentSeriesBinding
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.presentation.components.HeaderItem
import com.didik.moflix.presentation.components.MovieItem
import com.didik.moflix.presentation.features.series.detail.SeriesDetailActivity
import com.didik.moflix.utils.extensions.observeData
import com.didik.moflix.utils.helpers.CustomItemDecoration
import com.didik.moflix.utils.testing.EspressoIdlingResource
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeriesFragment : BindingFragment<FragmentSeriesBinding>() {

    private val seriesAdapter by lazy { GroupieAdapter() }

    private val viewModel: SeriesViewModel by viewModels()

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSeriesBinding {
        return FragmentSeriesBinding.inflate(inflater, container, false)
    }

    override fun renderView() {
        setupUI()
        setupSwipeRefresh()
        setupObserver()

        EspressoIdlingResource.start()
        viewModel.getSeries()
    }

    private fun setupUI() {
        binding.seriesRecyclerView.run {
            adapter = seriesAdapter
            addItemDecoration(
                CustomItemDecoration(
                    bottom = resources.getDimensionPixelSize(R.dimen.size_24dp)
                )
            )
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.getSeries()
        }
    }

    private fun setupObserver() {
        viewModel.series.observeData(viewLifecycleOwner) { series ->
            renderSeriesList(series)
        }

        viewModel.isLoading.observeData(viewLifecycleOwner) { isLoading ->
            binding.swipeRefreshLayout.isRefreshing = isLoading
        }

        viewModel.error.observeData(viewLifecycleOwner) { error ->
            toast(error)
        }
    }

    private fun renderSeriesList(seriesList: List<MovieModel>) {
        val headerItem = HeaderItem(getString(R.string.title_popular_series))
        val movieItems = seriesList.map { series ->
            MovieItem(series) {
                openSeriesDetail(series.id)
            }
        }
        seriesAdapter.run {
            clear()
            add(headerItem)
            addAll(movieItems)
        }

        if (EspressoIdlingResource.isNotIdleNow) {
            EspressoIdlingResource.end()
        }
    }

    private fun openSeriesDetail(seriesId: Int) {
        startActivity(
            SeriesDetailActivity.createIntent(
                context = requireContext(),
                seriesId = seriesId,
            )
        )
    }
}