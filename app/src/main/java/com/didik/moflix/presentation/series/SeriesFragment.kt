package com.didik.moflix.presentation.series

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.didik.moflix.R
import com.didik.moflix.core.BindingFragment
import com.didik.moflix.databinding.FragmentSeriesBinding
import com.didik.moflix.domain.entity.Movie
import com.didik.moflix.presentation.detail.MovieDetailActivity
import com.didik.moflix.utils.extensions.observeData
import com.didik.moflix.utils.helpers.MovieItemDecoration
import com.didik.moflix.views.HeaderItem
import com.didik.moflix.views.MovieItem
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeriesFragment : BindingFragment<FragmentSeriesBinding>() {

    private lateinit var moviesAdapter: GroupieAdapter
    private val seriesViewModel: SeriesViewModel by viewModels()

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
        moviesAdapter = GroupieAdapter()
        binding.seriesRecyclerView.run {
            adapter = moviesAdapter
            addItemDecoration(MovieItemDecoration(context))
        }
    }

    private fun setupObserver() {
        seriesViewModel.seriesList.observeData(viewLifecycleOwner, { seriesList ->
            renderMovieList(seriesList)
        })
    }

    private fun renderMovieList(movies: List<Movie>) {
        val headerItem = HeaderItem(getString(R.string.title_popular_series))
        val movieItems = movies.map { movie ->
            MovieItem(movie) {
                openMovieDetail(movie)
            }
        }
        moviesAdapter.run {
            add(headerItem)
            addAll(movieItems)
        }
    }

    private fun openMovieDetail(movie: Movie) {
        startActivity(MovieDetailActivity.newIntent(requireContext(), movie))
    }
}