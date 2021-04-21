package com.didik.moflix.presentation.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import com.didik.moflix.R
import com.didik.moflix.base.BindingFragment
import com.didik.moflix.databinding.FragmentMovieBinding
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.presentation.detail.MovieDetailActivity
import com.didik.moflix.utils.extensions.observeData
import com.didik.moflix.utils.helpers.MovieItemDecoration
import com.didik.moflix.utils.state.ViewState
import com.didik.moflix.views.HeaderItem
import com.didik.moflix.views.MovieItem
import com.xwray.groupie.GroupieAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MovieFragment : BindingFragment<FragmentMovieBinding>() {

    private lateinit var moviesAdapter: GroupieAdapter

    @Inject
    lateinit var movieViewModel: MovieViewModel

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentMovieBinding {
        return FragmentMovieBinding.inflate(inflater, container, false)
    }

    override fun renderView() {
        setupUI()
        setupObserver()
        movieViewModel.getMovies()
    }

    private fun setupUI() {
        moviesAdapter = GroupieAdapter()

        binding.moviesRecyclerView.run {
            adapter = moviesAdapter
            addItemDecoration(MovieItemDecoration(context))
        }
    }

    private fun setupObserver() {
        movieViewModel.movieState.observeData(viewLifecycleOwner) { state ->
            when (state) {
                is ViewState.RenderLoading -> showLoading(state.isLoading)
                is ViewState.RenderData -> renderMovieList(state.data)
                is ViewState.RenderError -> showToast(state.error)
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
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