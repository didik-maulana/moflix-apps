package com.didik.moflix.presentation.features.favorites.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.PagedList
import com.didik.moflix.R
import com.didik.moflix.base.BindingFragment
import com.didik.moflix.databinding.FragmentFavoriteMoviesBinding
import com.didik.moflix.domain.model.MovieModel
import com.didik.moflix.presentation.adapter.MovieAdapter
import com.didik.moflix.presentation.dialog.SortAlertDialog
import com.didik.moflix.presentation.features.movies.detail.MovieDetailActivity
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
class FavoriteMoviesFragment : BindingFragment<FragmentFavoriteMoviesBinding>(), CoroutineScope {

    private val job: Job by lazy { Job() }
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    private val viewModel: FavoriteMoviesViewModel by viewModels()
    private val moviesAdapter: MovieAdapter by lazy { MovieAdapter() }
    private var sortAlertDialog: SortAlertDialog? = null

    override fun initViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentFavoriteMoviesBinding {
        return FragmentFavoriteMoviesBinding.inflate(inflater, container, false)
    }

    override fun renderView() {
        setupMovieList()
        setupMovieSort()
        loadFavoriteMovies(Sort.NEWEST)
    }

    override fun onDestroyView() {
        job.cancel()
        super.onDestroyView()
    }

    private fun setupMovieList() {
        moviesAdapter.setOnItemClickListener { movieId ->
            startActivity(
                MovieDetailActivity.createIntent(
                    context = requireContext(),
                    movieId = movieId,
                )
            )
        }

        binding.favoriteMoviesRecyclerView.run {
            adapter = moviesAdapter
            addItemDecoration(
                CustomItemDecoration(
                    bottom = resources.getDimensionPixelSize(R.dimen.size_24dp)
                )
            )
        }
    }

    private fun setupMovieSort() {
        sortAlertDialog = SortAlertDialog(requireContext()).apply {
            setTitle(getString(R.string.title_favorites_order))
            setOnItemSelectedListener { sort ->
                viewModel.isSortedList = true
                loadFavoriteMovies(sort)
            }
        }.create()

        binding.sortingButton.setOnClickListener {
            sortAlertDialog?.show()
        }
    }

    private fun loadFavoriteMovies(sort: Sort) {
        launch {
            EspressoIdlingResource.start()
            viewModel.getFavoriteMovies(sort).observeData(viewLifecycleOwner) { movies ->
                if (movies.isNotEmpty()) {
                    renderMovieList(movies)
                    renderSortingButton(movies.size > 1)
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

    private fun renderMovieList(movies: PagedList<MovieModel>) {
        with(binding) {
            favoriteMoviesRecyclerView.isVisible = true
            emptyImageView.isVisible = false
            emptyTextView.isVisible = false
        }
        moviesAdapter.submitList(movies)

        if (viewModel.isSortedList) {
            binding.favoriteMoviesRecyclerView.scrollToPosition(0)
            viewModel.isSortedList = false
        }
    }

    private fun renderEmptyState() {
        with(binding) {
            favoriteMoviesRecyclerView.isVisible = false
            emptyImageView.isVisible = true
            emptyTextView.isVisible = true
        }
    }

    private fun renderSortingButton(isShow: Boolean) {
        binding.sortingButton.isVisible = isShow
    }

}