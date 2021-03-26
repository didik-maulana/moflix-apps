package com.didik.moflix.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.didik.moflix.R
import com.didik.moflix.core.BindingFragment
import com.didik.moflix.databinding.FragmentHomeBinding
import com.didik.moflix.domain.entity.Movie
import com.didik.moflix.presentation.detail.MovieDetailActivity
import com.didik.moflix.utils.extensions.observeData
import com.didik.moflix.utils.helpers.MovieItemDecoration
import com.didik.moflix.views.HeaderItem
import com.didik.moflix.views.MovieItem
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BindingFragment<FragmentHomeBinding>() {

    private lateinit var moviesAdapter: GroupieAdapter

    private val homeViewModel: HomeViewModel by viewModels()

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun renderView() {
        setupUI()
        setupObserver()
        homeViewModel.getMovies()
    }

    private fun setupUI() {
        moviesAdapter = GroupieAdapter()

        binding.moviesRecyclerView.run {
            adapter = moviesAdapter
            addItemDecoration(MovieItemDecoration(context))
        }
    }

    private fun setupObserver() {
        homeViewModel.movieList.observeData(viewLifecycleOwner) { movies ->
            renderMovieList(movies)
        }
    }

    private fun renderMovieList(movies: List<Movie>) {
        val headerItem = HeaderItem(getString(R.string.title_trending_now))
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