package com.didik.moflix.presentation.series

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.didik.moflix.R
import com.didik.moflix.base.BindingFragment
import com.didik.moflix.databinding.FragmentSeriesBinding
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.presentation.detail.MovieDetailActivity
import com.didik.moflix.utils.extensions.observeData
import com.didik.moflix.utils.extensions.toast
import com.didik.moflix.utils.helpers.CustomItemDecoration
import com.didik.moflix.utils.state.ViewState
import com.didik.moflix.views.HeaderItem
import com.didik.moflix.views.MovieItem
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SeriesFragment : BindingFragment<FragmentSeriesBinding>() {

    private val moviesAdapter by lazy { GroupieAdapter() }

    @Inject
    lateinit var seriesViewModel: SeriesViewModel

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentSeriesBinding {
        return FragmentSeriesBinding.inflate(inflater, container, false)
    }

    override fun renderView() {
        setupUI()
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

    private fun setupObserver() {
        seriesViewModel.seriesState.observeData(viewLifecycleOwner) { state ->
            when (state) {
                is ViewState.RenderLoading -> renderLoading(state.isLoading)
                is ViewState.RenderData -> renderSeriesList(state.data)
                is ViewState.RenderError -> context?.toast(state.error)
            }
        }
    }

    private fun renderLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }

    private fun renderSeriesList(movieModels: List<MovieModel>) {
        val headerItem = HeaderItem(getString(R.string.title_popular_series))
        val movieItems = movieModels.map { movie ->
            MovieItem(movie) {
                openSeriesDetail(movie.id)
            }
        }
        moviesAdapter.run {
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