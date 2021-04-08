package com.didik.moflix.presentation.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import com.didik.moflix.R
import com.didik.moflix.base.BindingFragment
import com.didik.moflix.databinding.FragmentMoviesBinding
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.presentation.detail.MovieDetailActivity
import com.didik.moflix.utils.extensions.observeData
import com.didik.moflix.utils.helpers.MovieItemDecoration
import com.didik.moflix.views.HeaderItem
import com.didik.moflix.views.MovieItem
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MoviesFragment : BindingFragment<FragmentMoviesBinding>() {

    private lateinit var moviesAdapter: GroupieAdapter

    @Inject
    lateinit var moviesViewModel: MoviesViewModel

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMoviesBinding {
        return FragmentMoviesBinding.inflate(inflater, container, false)
    }

    override fun renderView() {
        setupUI()
        setupObserver()
        moviesViewModel.getMovies()
    }

    private fun setupUI() {
        moviesAdapter = GroupieAdapter()

        binding.moviesRecyclerView.run {
            adapter = moviesAdapter
            addItemDecoration(MovieItemDecoration(context))
        }
    }

    private fun setupObserver() {
        moviesViewModel.movieList.observeData(viewLifecycleOwner) { movies ->
            renderMovieList(movies)
        }
    }

    private fun renderMovieList(movieModels: List<MovieModel>) {
        val headerItem = HeaderItem(getString(R.string.title_trending_now))
        val movieItems = movieModels.map { movie ->
            MovieItem(movie) {
                openMovieDetail(movie)
            }
        }
        moviesAdapter.run {
            add(headerItem)
            addAll(movieItems)
        }
    }

    private fun openMovieDetail(movieModel: MovieModel) {
        startActivity(MovieDetailActivity.newIntent(requireContext(), movieModel))
    }
}