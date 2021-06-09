package com.didik.moflix.presentation.features.movies.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.didik.moflix.R
import com.didik.moflix.base.BindingFragment
import com.didik.moflix.databinding.FragmentMovieBinding
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.presentation.features.movies.detail.MovieDetailActivity
import com.didik.moflix.utils.extensions.observeData
import com.didik.moflix.utils.helpers.CustomItemDecoration
import com.didik.moflix.utils.testing.EspressoIdlingResource
import com.didik.moflix.presentation.components.HeaderItem
import com.didik.moflix.presentation.components.MovieItem
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieFragment : BindingFragment<FragmentMovieBinding>() {

    private val moviesAdapter by lazy { GroupieAdapter() }

    private val movieViewModel: MovieViewModel by viewModels()

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMovieBinding {
        return FragmentMovieBinding.inflate(inflater, container, false)
    }

    override fun renderView() {
        setupUI()
        setupSwipeRefresh()
        setupObserver()

        EspressoIdlingResource.start()
        movieViewModel.getMovies()
    }

    private fun setupUI() {
        binding.moviesRecyclerView.run {
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
            movieViewModel.getMovies()
        }
    }

    private fun setupObserver() {
        movieViewModel.movies.observeData(viewLifecycleOwner) { movies ->
            renderMovieList(movies)
        }

        movieViewModel.isLoading.observeData(viewLifecycleOwner) { isLoading ->
            binding.swipeRefreshLayout.isRefreshing = isLoading
        }

        movieViewModel.error.observeData(viewLifecycleOwner) { error ->
            toast(error)
        }
    }

    private fun renderMovieList(movieModels: List<MovieModel>) {
        val headerItem = HeaderItem(getString(R.string.title_trending_now))
        val movieItems = movieModels.map { movie ->
            MovieItem(movie) {
                openMovieDetail(movie.id)
            }
        }
        moviesAdapter.run {
            clear()
            add(headerItem)
            addAll(movieItems)
        }

        if (EspressoIdlingResource.isNotIdleNow) {
            EspressoIdlingResource.end()
        }
    }

    private fun openMovieDetail(movieId: Int) {
        startActivity(
            MovieDetailActivity.createIntent(
                context = requireContext(),
                movieId = movieId,
            )
        )
    }
}