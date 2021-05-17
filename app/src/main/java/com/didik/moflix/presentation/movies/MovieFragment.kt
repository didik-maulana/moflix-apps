package com.didik.moflix.presentation.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.didik.moflix.R
import com.didik.moflix.base.BindingFragment
import com.didik.moflix.databinding.FragmentMovieBinding
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
class MovieFragment : BindingFragment<FragmentMovieBinding>() {

    private val moviesAdapter by lazy { GroupieAdapter() }

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
        binding.moviesRecyclerView.run {
            adapter = moviesAdapter
            addItemDecoration(
                CustomItemDecoration(
                    bottom = resources.getDimensionPixelSize(R.dimen.size_24dp)
                )
            )
        }
    }

    private fun setupObserver() {
        movieViewModel.movieState.observeData(viewLifecycleOwner) { state ->
            when (state) {
                is ViewState.RenderLoading -> renderLoading(state.isLoading)
                is ViewState.RenderData -> renderMovieList(state.data)
                is ViewState.RenderError -> context?.toast(state.error)
            }
        }
    }

    private fun renderLoading(isLoading: Boolean) {
        binding.progressBar.isVisible = isLoading
    }

    private fun renderMovieList(movieModels: List<MovieModel>) {
        val headerItem = HeaderItem(getString(R.string.title_trending_now))
        val movieItems = movieModels.map { movie ->
            MovieItem(movie) {
                openMovieDetail(movie.id)
            }
        }
        moviesAdapter.run {
            add(headerItem)
            addAll(movieItems)
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