package com.didik.moflix.presentation.series

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.didik.moflix.R
import com.didik.moflix.base.BindingFragment
import com.didik.moflix.databinding.FragmentSeriesBinding
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.presentation.detail.MovieDetailActivity
import com.didik.moflix.utils.extensions.observeData
import com.didik.moflix.utils.helpers.CustomItemDecoration
import com.didik.moflix.views.HeaderItem
import com.didik.moflix.views.MovieItem
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeriesFragment : BindingFragment<FragmentSeriesBinding>() {

    private val moviesAdapter by lazy { GroupieAdapter() }

    private val seriesViewModel: SeriesViewModel by viewModels()

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
        seriesViewModel.getSeries()
    }

    private fun setupUI() {
        binding.seriesRecyclerView.run {
            adapter = moviesAdapter
            addItemDecoration(
                CustomItemDecoration(
                    bottom = resources.getDimensionPixelSize(R.dimen.size_24dp)
                )
            )
        }
    }

    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            seriesViewModel.getSeries()
        }
    }

    private fun setupObserver() {
        seriesViewModel.series.observeData(viewLifecycleOwner) { series ->
            renderSeriesList(series)
        }

        seriesViewModel.isLoading.observeData(viewLifecycleOwner) { isLoading ->
            binding.swipeRefreshLayout.isRefreshing = isLoading
        }

        seriesViewModel.error.observeData(viewLifecycleOwner) { error ->
            toast(error)
        }
    }

    private fun renderSeriesList(movieModels: List<MovieModel>) {
        val headerItem = HeaderItem(getString(R.string.title_popular_series))
        val movieItems = movieModels.map { movie ->
            MovieItem(movie) {
                openSeriesDetail(movie.id)
            }
        }
        moviesAdapter.run {
            clear()
            add(headerItem)
            addAll(movieItems)
        }
    }

    private fun openSeriesDetail(seriesId: Int) {
        startActivity(
            MovieDetailActivity.createIntent(
                context = requireContext(),
                seriesId = seriesId,
            )
        )
    }
}